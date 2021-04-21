package com.antplatform.admin.service;

import com.antplatform.admin.api.ServerMgtApi;
import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.utils.DataUtil;
import com.antplatform.admin.common.utils.FileUtil;
import com.antplatform.admin.common.utils.IpUtil;
import com.antplatform.admin.common.utils.MathUtil;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:50:29
 * @description:
 */
@Service("ServerMgtApi")
public class ServerMgtPortService implements ServerMgtApi {

    private final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 查询服务信息
     *
     * @return
     */
    @Override
    public Response<ServerDTO> findBySpec() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();

        ServerDTO serverDTO = new ServerDTO();
        // 系统信息
        serverDTO.setSystem(getSystemInfo(os));
        // cpu 信息
        serverDTO.setCpu(getCpuInfo(hal.getProcessor()));
        // jvm 信息
        serverDTO.setJvm(getJvmInfo());
        // 内存信息
        serverDTO.setMemory(getMemoryInfo(hal.getMemory()));
        // 交换区信息
        serverDTO.setSwap(getSwapInfo(hal.getMemory()));
        // 磁盘
        serverDTO.setDisk(getDiskInfo(os));

        serverDTO.setTime(DataUtil.getTodayTime(DataUtil.TIME_FORMAT));

        return Responses.of(serverDTO);
    }

    /**
     * 获取系统相关信息,系统、运行天数、系统IP
     * @param os /
     * @return /
     */
    private Map<String,Object> getSystemInfo(OperatingSystem os){
        Map<String,Object> systemInfo = new LinkedHashMap<>();
        // jvm 运行时间
        Date startDate = DataUtil.getServerStartDate();
        Date nowDate = DataUtil.getNowDate();
        // 计算项目运行时间
        String runTime = DataUtil.getDatePoor(startDate,nowDate, Constant.TimeType.HOUR);
        // 系统信息
        Properties props = System.getProperties();

        systemInfo.put("os", os.toString());

        systemInfo.put("startTime",DataUtil.getTime(startDate,DataUtil.DATE_TIME_HOUR_MINUTE_SECOND));
        systemInfo.put("runTime", runTime);
        systemInfo.put("ip", IpUtil.getLocalIp());
//        systemInfo.put("ip", NetWorkUtil.getHostIp());
//        systemInfo.put("hostName",NetWorkUtil.getHostName());
        systemInfo.put("osName",props.getProperty("os.name"));
        systemInfo.put("osArch",props.getProperty("os.arch"));
        systemInfo.put("dir",props.getProperty("user.dir"));
        return systemInfo;
    }

    /**
     * 获取Cpu相关信息
     * @param processor /
     * @return /
     */
    private Map<String,Object> getCpuInfo(CentralProcessor processor) {
        Map<String,Object> cpuInfo = new LinkedHashMap<>();
        cpuInfo.put("name", processor.getProcessorIdentifier().getName());
        cpuInfo.put("package", processor.getPhysicalPackageCount() + "个物理CPU");
        cpuInfo.put("core", processor.getPhysicalProcessorCount() + "个物理核心");
        cpuInfo.put("coreNumber", processor.getPhysicalProcessorCount());
        cpuInfo.put("logic", processor.getLogicalProcessorCount() + "个逻辑CPU");
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 等待1秒...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + ioWait + irq + softIrq + steal;
        cpuInfo.put("used", df.format(100d * user / totalCpu + 100d * sys / totalCpu));
        cpuInfo.put("idle", df.format(100d * idle / totalCpu));

        cpuInfo.put("userUsed", MathUtil.round(MathUtil.mul(MathUtil.div(user,totalCpu), 100), 2));
        cpuInfo.put("sysUsed", MathUtil.round(MathUtil.mul(MathUtil.div(sys,totalCpu), 100), 2));
        cpuInfo.put("free", MathUtil.round(MathUtil.mul(MathUtil.div(idle,totalCpu), 100), 2));
        return cpuInfo;
    }

    /**
     * 设置Java虚拟机
     */
    private Map<String,Object> getJvmInfo() {
        Map<String,Object> jvmInfo = new LinkedHashMap<>();
        Properties props = System.getProperties();
        jvmInfo.put("total",FormatUtil.formatBytes(Runtime.getRuntime().totalMemory()));
        jvmInfo.put("max",FormatUtil.formatBytes(Runtime.getRuntime().maxMemory()));
        jvmInfo.put("free",FormatUtil.formatBytes(Runtime.getRuntime().freeMemory()));
        jvmInfo.put("version",props.getProperty("java.version"));
        jvmInfo.put("home",props.getProperty("java.home"));
        jvmInfo.put("name", ManagementFactory.getRuntimeMXBean().getVmName());

        jvmInfo.put("used", FormatUtil.formatBytes(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        jvmInfo.put("usageRate", df.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(double)Runtime.getRuntime().totalMemory() * 100));

        return jvmInfo;
    }

    /**
     * 获取内存信息
     * @param memory /
     * @return /
     */
    private Map<String,Object> getMemoryInfo(GlobalMemory memory) {
        Map<String,Object> memoryInfo = new LinkedHashMap<>();
        memoryInfo.put("total", FormatUtil.formatBytes(memory.getTotal()));
        memoryInfo.put("available", FormatUtil.formatBytes(memory.getAvailable()));
        memoryInfo.put("used", FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        memoryInfo.put("usageRate", df.format((memory.getTotal() - memory.getAvailable())/(double)memory.getTotal() * 100));
        return memoryInfo;
    }

    /**
     * 获取交换区信息
     * @param memory /
     * @return /
     */
    private Map<String,Object> getSwapInfo(GlobalMemory memory) {
        Map<String,Object> swapInfo = new LinkedHashMap<>();
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        long total = virtualMemory.getSwapTotal();
        long used = virtualMemory.getSwapUsed();
        swapInfo.put("total", FormatUtil.formatBytes(total));
        swapInfo.put("used", FormatUtil.formatBytes(used));
        swapInfo.put("available", FormatUtil.formatBytes(total - used));
        if(used == 0){
            swapInfo.put("usageRate", 0);
        } else {
            swapInfo.put("usageRate", df.format(used/(double)total * 100));
        }
        return swapInfo;
    }

    /**
     * 获取磁盘信息
     * @return /
     */
    private Map<String,Object> getDiskInfo(OperatingSystem os) {
        Map<String,Object> diskInfo = new LinkedHashMap<>();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        String osName = System.getProperty("os.name");
        long available = 0, total = 0;
        for (OSFileStore fs : fsArray){
            // windows 需要将所有磁盘分区累加，linux 和 mac 直接累加会出现磁盘重复的问题，待修复
            if(osName.toLowerCase().startsWith(Constant.WIN)) {
                available += fs.getUsableSpace();
                total += fs.getTotalSpace();
            } else {
                available = fs.getUsableSpace();
                total = fs.getTotalSpace();
                break;
            }
        }
        long used = total - available;
        diskInfo.put("total", total > 0 ? FileUtil.getSize(total) : "?");
        diskInfo.put("available", FileUtil.getSize(available));
        diskInfo.put("used", FileUtil.getSize(used));
        if(total != 0){
            diskInfo.put("usageRate", df.format(used/(double)total * 100));
        } else {
            diskInfo.put("usageRate", 0);
        }
        return diskInfo;
    }
}

//package com.antplatform.admin.service;
//
//import com.antplatform.admin.api.dto.PermissionDTO;
//import com.antplatform.admin.common.dto.TreeDTO;
//import com.antplatform.admin.biz.model.Permission;
//import com.antplatform.admin.service.port.mapper.PermissionMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author: maoyan
// * @date: 2020/10/22 16:21:30
// * @description:
// */
//@Component
//public class PortService {
//
//    @Autowired
//    private PermissionMapper permissionMapper;
//
//    public Collection<PermissionDTO> getPermissionTree(Collection<Permission> permissions,Boolean flag){
//
//        Collection<PermissionDTO> permissionDTOS = permissionMapper.toDto(permissions);
//
//        if (flag){
//            List<PermissionDTO> menus = this.assembleResourceTree(permissionDTOS);
//
//            return menus;
//        }
//        return permissionDTOS;
//    }
//
//
//    /**
//     * 组装子父级目录
//     * @param resourceList
//     * @return
//     */
////    private List<PermissionDTO> assembleResourceTree(Collection<PermissionDTO> resourceList) {
////        Map<Integer, PermissionDTO> resourceMap = new HashMap<>();
////        List<PermissionDTO> menus = new ArrayList<>();
////        for (PermissionDTO resource : resourceList) {
////            resourceMap.put(resource.getId(), resource);
////        }
////        for (PermissionDTO resource : resourceList) {
////            Integer treePId = resource.getParentId();
////            PermissionDTO resourceTree = resourceMap.get(treePId);
////            if (null != resourceTree && !resource.equals(resourceTree)) {
////                List<PermissionDTO> nodes = resourceTree.getChildren();
////                if (null == nodes) {
////                    nodes = new ArrayList<>();
////                    resourceTree.setChildren(nodes);
////                }
////                nodes.add(resource);
////            } else {
////                menus.add(resource);
////            }
////        }
////        return menus;
////    }
//
//
//    private <T extends TreeDTO> List<T> assembleResourceTree(Collection<T> resourceList) {
//        Map<Integer, T> resourceMap = new HashMap<>();
//        List<T> menus = new ArrayList<>();
//        for (T resource : resourceList) {
//            resourceMap.put(resource.getId(), resource);
//        }
//        for (T resource : resourceList) {
//            Integer treePId = resource.getParentId();
//            T resourceTree = resourceMap.get(treePId);
//            if (null != resourceTree && !resource.equals(resourceTree)) {
//                List<T> nodes = resourceTree.getChildren();
//                if (null == nodes) {
//                    nodes = new ArrayList<>();
//                    resourceTree.setChildren(nodes);
//                }
//                nodes.add(resource);
//            } else {
//                menus.add(resource);
//            }
//        }
//        return menus;
//    }
//}

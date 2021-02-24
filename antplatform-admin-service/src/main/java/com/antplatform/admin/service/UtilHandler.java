package com.antplatform.admin.service;

import com.antplatform.admin.common.dto.TreeDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2020/10/22 19:36:12
 * @description:
 */
public class UtilHandler {

    public static  <T extends TreeDTO> List<T> assembleResourceTree(Collection<T> resourceList) {
        Map<Integer, T> resourceMap = new HashMap<>();
        List<T> menus = new ArrayList<>();
        for (T resource : resourceList) {
            resourceMap.put(resource.getId(), resource);
        }
        for (T resource : resourceList) {
            Integer treePId = resource.getParentId();
            T resourceTree = resourceMap.get(treePId);
            if (null != resourceTree && !resource.equals(resourceTree)) {
                List<T> nodes = resourceTree.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<>();
                    resourceTree.setChildren(nodes);
                }
                nodes.add(resource);
            } else {
                menus.add(resource);
            }
        }
        return menus;
    }
}

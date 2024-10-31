package com.store.cincomenos.utils.user.generator;

import java.util.List;

public class RoleHierarchy {

    private List<String> hierarchy;
    
    public RoleHierarchy(List<String> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public List<String> getHierarchy() {
        return hierarchy;
    }

    public int getIndex(String role) {
        return hierarchy.indexOf(role);
    }
}

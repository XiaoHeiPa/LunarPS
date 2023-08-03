package org.cubewhy.lunarcn;

import org.cubewhy.lunarcn.loader.api.ModInitializer;

public class LunarPS implements ModInitializer {
    @Override
    public void preInit() {
        System.out.println("[LunarPS] Injecting!");
    }
}

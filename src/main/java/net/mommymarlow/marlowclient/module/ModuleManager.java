package net.mommymarlow.marlowclient.module;

import net.mommymarlow.marlowclient.module.impl.combat.*;
import net.mommymarlow.marlowclient.module.impl.misc.AutoRespawn;
import net.mommymarlow.marlowclient.module.impl.movement.Flight;
import net.mommymarlow.marlowclient.module.impl.movement.Sprint;
import net.mommymarlow.marlowclient.module.impl.render.NickHider;
import net.mommymarlow.marlowclient.module.impl.render.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<Module> modules = new ArrayList<>();

    public static final ModuleManager INSTANCE = new ModuleManager();

    public ModuleManager() {
        init();
    }

    private void init() {
        //COMBAT
        add(new AutoAnchor());
        add(new AutoCrystal());
        add(new AutoTotem());
        add(new CrystalOptimizer());
        add(new SwapShield());
        add(new SwapCrystal());
        add(new SwapObsidian());
        add(new TriggerBot());
        add(new TotemInvRestock());

        //MOVEMENT
        add(new Flight("Flight  ", "  unfinished", Category.MOVEMENT));
        add(new Sprint());

        //RENDER
        add(new Arraylist());
        add(new Cape());
        add(new FullBright());
        add(new NickHider());
        add(new NickPing());
        add(new NoHurtCam());
        add(new SlowAttack());

        //MISC
        add(new AutoRespawn());
    }


    public void add(Module m) {
        modules.add(m);
    }

    public void remove(Module m) {
        modules.remove(m);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name){

        for(Module module : modules) {
            if(module.getName().equals(name)) return module;
        }

        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> modules = new ArrayList<>();
        for(Module m : this.modules){
            if(m.getCategory().equals(category)){
                modules.add(m);
            }
        }
        return modules;
    }

    public Module getModuleByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls) {
                continue;
            }
            return m;
        }
        return null;
    }

    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();
        for(Module m : getModules()) {
            if(m.isEnabled()) {
                enabled.add(m);
            }
        }

        return enabled;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}

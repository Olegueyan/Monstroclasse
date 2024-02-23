package net.olegueyan.monstroclasse.module;

import net.olegueyan.monstroclasse.entity.Module;
import net.olegueyan.monstroclasse.repository.ModuleRepository;

import java.util.*;

/**
 * Registerer of module
 */
public class ModuleRegisterBus
{
    // ------- Registered modules ------- //
    public static Map<String, IsModule> moduleMap = new HashMap<>();
    // ---------------------------------- //

    // ***************************************************************
    // ModuleRegisterBus - METHODS
    // ***************************************************************

    /**
     * Register a module
     * @param module IsModule
     */
    public static void registerModule(IsModule module)
    {
        moduleMap.put(Objects.requireNonNull(ModuleRepository.read(module.reference())).getReference(), module);
    }

    /**
     * Get all modules as ArrayList<IsModule> from List<Module>
     * @param modules List<Module>
     * @return ArrayList<IsModule>
     */
    public static ArrayList<IsModule> modulesAvailable(List<Module> modules)
    {
        ArrayList<IsModule> isModules = new ArrayList<>();
        modules.forEach(m -> isModules.add(moduleMap.get(m.getReference())));
        return isModules;
    }

    /**
     * Get all "Admin" modules
     * @return ArrayList<IsModule>
     */
    public static ArrayList<IsModule> modulesAdmin()
    {
        ArrayList<IsModule> isModules = new ArrayList<>();
        moduleMap.values().forEach(m ->
        {
            if (m.admin())
            {
                isModules.add(m);
            }
        });
        return isModules;
    }

    /**
     * Get all "NonAdmin" modules
     * @return ArrayList<IsModule>
     */
    public static ArrayList<IsModule> modulesNonAdmin()
    {
        ArrayList<IsModule> isModules = new ArrayList<>();
        moduleMap.values().forEach(m ->
        {
            if (!m.admin())
            {
                isModules.add(m);
            }
        });
        return isModules;
    }

    // ***************************************************************
    // END
    // ***************************************************************
}
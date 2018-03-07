package fr.poudlardrp.citizens.trait;

import fr.poudlardrp.citizens.Citizens;
import fr.poudlardrp.citizens.api.CitizensAPI;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.scripting.CompileCallback;
import fr.poudlardrp.citizens.api.scripting.Script;
import fr.poudlardrp.citizens.api.scripting.ScriptFactory;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TraitName("scripttrait")
public class ScriptTrait extends Trait {
    private final List<RunnableScript> runnableScripts = new ArrayList<RunnableScript>();
    @Persist
    public List<String> files = new ArrayList<String>();

    public ScriptTrait() {
        super("scripttrait");
    }

    public void addScripts(List<String> scripts) {
        for (String f : scripts) {
            if (!files.contains(f) && validateFile(f)) {
                loadScript(f);
                files.add(f);
            }
        }
    }

    public List<String> getScripts() {
        return files;
    }

    @Override
    public void load(DataKey key) {
        for (String file : files) {
            if (validateFile(file)) {
                loadScript(file);
            }
        }
    }

    public void loadScript(final String file) {
        File f = new File(JavaPlugin.getPlugin(Citizens.class).getScriptFolder(), file);
        CitizensAPI.getScriptCompiler().compile(f).cache(true).withCallback(new CompileCallback() {
            @Override
            public void onScriptCompiled(String sourceDescriptor, ScriptFactory compiled) {
                final Script newInstance = compiled.newInstance();
                Bukkit.getScheduler().scheduleSyncDelayedTask(CitizensAPI.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            newInstance.invoke("onLoad", npc);
                        } catch (RuntimeException e) {
                            if (!(e.getCause() instanceof NoSuchMethodException)) {
                                throw e;
                            }
                        }
                        runnableScripts.add(new RunnableScript(newInstance, file));
                    }
                });
            }
        }).beginWithFuture();
    }

    public void removeScripts(List<String> scripts) {
        files.removeAll(scripts);
        Iterator<RunnableScript> itr = runnableScripts.iterator();
        while (itr.hasNext()) {
            if (scripts.remove(itr.next().file)) {
                itr.remove();
            }
        }
    }

    @Override
    public void run() {
        Iterator<RunnableScript> itr = runnableScripts.iterator();
        while (itr.hasNext()) {
            try {
                itr.next().script.invoke("run", npc);
            } catch (RuntimeException e) {
                if (e.getCause() instanceof NoSuchMethodException) {
                    itr.remove();
                } else {
                    throw e;
                }
            }
        }
    }

    public boolean validateFile(String file) {
        File f = new File(JavaPlugin.getPlugin(Citizens.class).getScriptFolder(), file);
        if (!f.exists() || !f.getParentFile().equals(JavaPlugin.getPlugin(Citizens.class).getScriptFolder())) {
            return false;
        }
        return CitizensAPI.getScriptCompiler().canCompile(f);
    }

    private static class RunnableScript {
        String file;
        Script script;

        public RunnableScript(Script script, String file) {
            this.script = script;
            this.file = file;
        }
    }
}

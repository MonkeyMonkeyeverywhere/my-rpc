package com.lw.myrpc.producer.reflection;

import com.google.common.collect.Lists;
import com.lw.myrpc.common.annotation.MyRpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName : MyRpcServiceScanner
 * @Description : scan all the my rpc service implements
 * @Author : lw.tar.gz
 * @Date: 2020-06-11 14:32
 */
@Slf4j
public class MyRpcServiceScanner {

    // todo how to solve the match problem,it can be serviceImpl or Iservice to service
    public static ConcurrentHashMap<String, Class<?>> rpcImpMap = new ConcurrentHashMap<>();

    /**
     * start scan all the annotated class,probably can extract to another place
     */
    public static void doScan() {
        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap
                = scanClassesByAnnotations("com.lw.myrpc.producer", true, Lists.newArrayList(MyRpcService.class));
        Set<Class<?>> classes = classSetMap.get(MyRpcService.class);
        classes.forEach(c -> {
            rpcImpMap.put(c.getSimpleName(), c);
        });
    }

    private static final String EXT = "class";

    public static String getPkgPath(String pkgName) {
        String pkgDirName = pkgName.replace('.', File.separatorChar);
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);
        return Objects.nonNull(url) ? url.getFile() : null;
    }

    public static Set<Class<?>> scanClasses(String pkgName, final boolean recursive) {
        Set<Class<?>> classesSet = new HashSet<>();

        Collection<File> allClassFile = getAllClassFile(getPkgPath(pkgName), recursive);

        for (File curFile : allClassFile) {
            try {
                classesSet.add(getClassObj(curFile, pkgName));
            } catch (ClassNotFoundException e) {
                log.error("load class fail", e);
            }
        }

        return classesSet;
    }

    private static Class<?> getClassObj(File file, String pkgName) throws ClassNotFoundException {
        String absPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - EXT.length() - 1);
        String className = absPath.substring(getPkgPath(pkgName).length()).replace(File.separatorChar, '.');
        className = className.startsWith(".") ? pkgName + className : pkgName + "." + className;
        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }

    private static Collection<File> getAllClassFile(String pkgPath, boolean recursive) {
        File fPkgDir = new File(pkgPath);
        if (!(fPkgDir.exists() && fPkgDir.isDirectory())) {
            log.error("the directory to package is empty: {}", pkgPath);
            return null;
        }
        return FileUtils.listFiles(fPkgDir, new String[]{EXT}, recursive);
    }

    public static Map<Class<? extends Annotation>, Set<Class<?>>> scanClassesByAnnotations(
            String pkgName, final boolean recursive, List<Class<? extends Annotation>> targetAnnotations) {
        Map<Class<? extends Annotation>, Set<Class<?>>> resultMap = new HashMap<>(16);

        Collection<File> allClassFile = getAllClassFile(getPkgPath(pkgName), recursive);

        for (File curFile : allClassFile) {
            try {
                Class<?> curClass = getClassObj(curFile, pkgName);
                for (Class<? extends Annotation> annotation : targetAnnotations) {
                    if (curClass.isAnnotationPresent(annotation)) {
                        if (!resultMap.containsKey(annotation)) {
                            resultMap.put(annotation, new HashSet<Class<?>>());
                        }
                        resultMap.get(annotation).add(curClass);
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("load class fail", e);
            }
        }

        return resultMap;
    }

}

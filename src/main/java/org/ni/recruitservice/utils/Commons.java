package org.ni.recruitservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * Created by nazmul on 9/21/2018.
 */

public class Commons {
    private static Logger log = LoggerFactory.getLogger(Commons.class);

    private static InputStream propIS = null;
    private static Properties props = new Properties();

    static{
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        propIS = classloader.getResourceAsStream("application.properties");
        try {
            props.load(propIS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(List list) {
        if (list == null) {

            return true;
        }
        if (list.size() == 0) {

            return true;
        }

        return false;
    }

    public static boolean isEmpty(String string) {
        if (string == null) {

            return true;
        }
        if ("".equals(string)) {

            return true;
        }

        return false;
    }

    public static boolean isEmpty(Map map) {
        if (map == null) {

            return true;
        }
        if (map.isEmpty()) {

            return true;
        }

        return false;
    }
}

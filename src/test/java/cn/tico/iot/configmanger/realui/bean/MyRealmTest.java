package cn.tico.iot.configmanger.realui.bean;

import cn.tico.iot.configmanger.TestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.log.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class MyRealmTest //extends TestBase {
{
    @Before
    public void setUp() throws Exception {
       // jedis.set("login:pro:29a705221b9041aaa42d3b212ef93d81","test");
    }

    @After
    public void tearDown() throws Exception {
       // jedis.del("login:pro:29a705221b9041aaa42d3b212ef93d81");
    }


    @Test
    public void 我在登陆() throws IOException {
        Logs.get().debug("my login ");
//        SingleCommand<YourClass> parser = SingleCommand.singleCommand(YourClass.class);
//        YourClass cmd = parser.parse(args);
//
//        // Execute your command however is appropriate e.g.
//        cmd.run();
        Process process = Runtime.getRuntime().exec("curl -I -v  -X OPTIONS " +
                "-H \"Origin: http://127.0.0.1:8080\" " +
                "http://127.0.0.1:8090/graph/device\\?sno\\=FD161631301AD7 " +
                "-H 'access_token: 29a705221b9041aaa42d3b212ef93d81'\n");
        InputStream is = process.getInputStream();
        byte[] b = new byte[1024];
        int size;
        while((size = is.read(b)) != -1){
            System.out.println(new String(b,0,size,"GBK"));

        }



    }
//    @Command(name = "checksum", mixinStandardHelpOptions = true, version = "checksum 4.0",
//            description = "Prints the checksum (MD5 by default) of a file to STDOUT.")
//    class YourClass implements Callable {
//
//        @Override
//        public Object call() throws Exception {
//            return null;
//        }
//    }


    @Test
    public void doGetAuthorizationInfo() {
    }

    @Test
    public void doGetAuthenticationInfo() {
    }

    @Test
    public void isToken() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void setAuthorizationCache() {
    }

    @Test
    public void getAuthorizationCache() {
    }

    @Test
    public void getAuthorizationCacheName() {
    }

    @Test
    public void setAuthorizationCacheName() {
    }

    @Test
    public void isAuthorizationCachingEnabled() {
    }

    @Test
    public void setAuthorizationCachingEnabled() {
    }

    @Test
    public void getPermissionResolver() {
    }

    @Test
    public void setPermissionResolver() {
    }

    @Test
    public void getRolePermissionResolver() {
    }

    @Test
    public void setRolePermissionResolver() {
    }

    @Test
    public void onInit() {
    }

    @Test
    public void afterCacheManagerSet() {
    }

    @Test
    public void getAuthorizationInfo() {
    }

    @Test
    public void getAuthorizationCacheKey() {
    }

    @Test
    public void clearCachedAuthorizationInfo() {
    }

    @Test
    public void doGetAuthorizationInfo1() {
    }

    @Test
    public void getPermissions() {
    }

    @Test
    public void isPermitted() {
    }

    @Test
    public void isPermitted1() {
    }

    @Test
    public void isPermitted2() {
    }

    @Test
    public void isPermitted3() {
    }

    @Test
    public void isPermitted4() {
    }

    @Test
    public void isPermitted5() {
    }

    @Test
    public void isPermittedAll() {
    }

    @Test
    public void isPermittedAll1() {
    }

    @Test
    public void isPermittedAll2() {
    }

    @Test
    public void checkPermission() {
    }

    @Test
    public void checkPermission1() {
    }

    @Test
    public void checkPermission2() {
    }

    @Test
    public void checkPermissions() {
    }

    @Test
    public void checkPermissions1() {
    }

    @Test
    public void checkPermissions2() {
    }

    @Test
    public void hasRole() {
    }

    @Test
    public void hasRole1() {
    }

    @Test
    public void hasRoles() {
    }

    @Test
    public void hasRoles1() {
    }

    @Test
    public void hasAllRoles() {
    }

    @Test
    public void checkRole() {
    }

    @Test
    public void checkRole1() {
    }

    @Test
    public void checkRoles() {
    }

    @Test
    public void checkRoles1() {
    }

    @Test
    public void checkRoles2() {
    }

    @Test
    public void doClearCache() {
    }

    @Test
    public void getCredentialsMatcher() {
    }

    @Test
    public void setCredentialsMatcher() {
    }

    @Test
    public void getAuthenticationTokenClass() {
    }

    @Test
    public void setAuthenticationTokenClass() {
    }

    @Test
    public void setAuthenticationCache() {
    }

    @Test
    public void getAuthenticationCache() {
    }

    @Test
    public void getAuthenticationCacheName() {
    }

    @Test
    public void setAuthenticationCacheName() {
    }

    @Test
    public void isAuthenticationCachingEnabled() {
    }

    @Test
    public void setAuthenticationCachingEnabled() {
    }

    @Test
    public void setName1() {
    }

    @Test
    public void supports() {
    }

    @Test
    public void init() {
    }

    @Test
    public void onInit1() {
    }

    @Test
    public void afterCacheManagerSet1() {
    }

    @Test
    public void isAuthenticationCachingEnabled1() {
    }

    @Test
    public void getAuthenticationInfo() {
    }

    @Test
    public void assertCredentialsMatch() {
    }

    @Test
    public void getAuthenticationCacheKey() {
    }

    @Test
    public void getAuthenticationCacheKey1() {
    }

    @Test
    public void doClearCache1() {
    }

    @Test
    public void clearCachedAuthenticationInfo() {
    }

    @Test
    public void doGetAuthenticationInfo1() {
    }

    @Test
    public void getCacheManager() {
    }

    @Test
    public void setCacheManager() {
    }

    @Test
    public void isCachingEnabled() {
    }

    @Test
    public void setCachingEnabled() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void setName2() {
    }

    @Test
    public void afterCacheManagerSet2() {
    }

    @Test
    public void onLogout() {
    }

    @Test
    public void clearCache() {
    }

    @Test
    public void doClearCache2() {
    }

    @Test
    public void getAvailablePrincipal() {
    }
}
package com.lxhook.v849;

import android.os.Build;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.util.UUID;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpp) throws Throwable {
        if (!"com.zenmen.palmchat".equals(lpp.packageName)) return;

        // 屏蔽ROOT检测
        try {
            XposedHelpers.findAndHookMethod("com.zenmen.palmchat.utils.k", lpp.classLoader, "b", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.setResult(false);
                }
            });
        } catch (Exception e) {}

        // 屏蔽Xposed检测
        try {
            XposedHelpers.findAndHookMethod("com.zenmen.palmchat.utils.k", lpp.classLoader, "c", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    param.setResult(false);
                }
            });
        } catch (Exception e) {}

        // 伪造设备信息
        try {
            XposedHelpers.setStaticField(Build.class, "BRAND", "Xiaomi");
            XposedHelpers.setStaticField(Build.class, "MODEL", "Redmi Note 12");
            XposedHelpers.setStaticField(Build.class, "MANUFACTURER", "Xiaomi");
            XposedHelpers.setStaticField(Build.class, "SERIAL", UUID.randomUUID().toString().replace("-", "").substring(0, 12));
            XposedHelpers.setStaticField(Build.VERSION.class, "RELEASE", "13");
        } catch (Exception e) {}

        // 伪造UNID
        try {
            XposedHelpers.findAndHookMethod("com.zenmen.palmchat.utils.b", lpp.classLoader, "q", new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) {
            param.setResult("LX" + UUID.randomUUID().toString().replace("-", "").substring(0, 28).toUpperCase());
        }
    });
} catch (Exception e) {}

// 伪造ckey
try {
    XposedHelpers.findAndHookMethod("com.zenmen.palmchat.utils.b", lpp.classLoader, "w", new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) {
            param.setResult(UUID.randomUUID().toString().replace("-", ""));
        }
    });
} catch (Exception e) {}

// 绕过签名校验
try {
    XposedHelpers.findAndHookMethod("com.zenmen.palmchat.utils.i", lpp.classLoader, "a", android.content.Context.class, new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) {
            param.setResult(true);
        }
    });
} catch (Exception e) {}

}
}

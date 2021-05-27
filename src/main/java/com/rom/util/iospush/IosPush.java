package com.rom.util.iospush;

//import javapns.back.PushNotificationManager;
//import javapns.back.SSLConnectionHelper;
//import javapns.data.Device;
//import javapns.data.PayLoad;


public class IosPush {

//    public static void main(String[] args) throws Exception {
//        try {
//            //从客户端获取的deviceToken，在此为了测试简单，写固定的一个测试设备标识。
//            String deviceToken = "7ee4fa40739a0dcd13c8c1f1b8602e4c65d812f919644d5f5d27181f32916221";
//            System.out.println("Push Start deviceToken:" + deviceToken);
//            //定义消息模式
//            PayLoad payLoad = new PayLoad();
//            payLoad.addAlert("this is test!");
//            payLoad.addBadge(1000);//消息推送标记数，小红圈中显示的数字。
//            payLoad.addSound("default");
//            //注册deviceToken
//            PushNotificationManager pushManager = PushNotificationManager.getInstance();
//            pushManager.addDevice("iPhone", deviceToken);
//            //连接APNS
//            String host = "gateway.sandbox.push.apple.com";
//            //String host = "gateway.push.apple.com";
//            int port = 2195;
//            String certificatePath = "e:/zhengshu.p12";//前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
//            String certificatePassword = "123";//p12文件密码。
//            pushManager.initializeConnection(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//            //发送推送
//            Device client = pushManager.getDevice("iPhone");
//            System.out.println("推送消息: " + client.getToken() + "\n" + payLoad.toString() + " ");
//            pushManager.sendNotification(client, payLoad);
//            //停止连接APNS
//            pushManager.stopConnection();
//            //删除deviceToken
//            pushManager.removeDevice("iPhone");
//            System.out.println("Push End");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}

package cc.gengkeke.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author gengkeke
 * @title: 3.7 权限控制 通过设置Zookeeper服务器上数据节点的ACL控制，
 * 就可以对其客户端对该数据节点的访问权限：如果符合ACL控制，则可以进行访问，否则无法访问。
 * @date 2018/8/1 17:14
 */
//① 使用无权限信息的Zookeeper会话访问含权限信息的数据节点　　
public class AuthSample_Get {
    final static String PATH = "/zk-book-auth_test";

    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + PATH);
        ZooKeeper zookeeper2 = new ZooKeeper("127.0.0.1:2181", 5000, null);
        zookeeper2.getData(PATH, false, null);
    }
}

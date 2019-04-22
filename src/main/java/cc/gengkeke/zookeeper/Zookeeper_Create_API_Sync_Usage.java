package cc.gengkeke.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.ZooDefs.Ids;
/**
 * @author gengkeke
 * @title: 3.1  创建节点 都不支持递归调用，即无法在父节点不存在的情况下创建一个子节点
 * @date 2018/8/1 17:19
 */
//同步方式
    public class Zookeeper_Create_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_Create_API_Sync_Usage());
        System.out.println(zookeeper.getState());
        connectedSemaphore.await();

        String path1 = zookeeper.create("/bdops", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        //String path2 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
       // System.out.println("Success create znode: " + path2);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}

package cn.klzhong.samples.mongodb;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoUtil {
    private static final Logger log = LoggerFactory.getLogger(MongoUtil.class);

    private MongoClient _mongoClient;
    private String mongoPropsFile = "/opt/upsmart/upsmart-config/kurast2/rulemanager/mongo.properties";

    private String replsetStr;
    private List<ServerAddress> serverAddrs = new ArrayList<ServerAddress>();

    private String dbName;

    private static MongoUtil _instance;

    private MongoUtil() {
        loadConfig();
        initMongo();
    }

    public static synchronized MongoUtil getInstance() {
        if (_instance == null) {
            _instance = new MongoUtil();
        }
        return _instance;
    }

    public MongoClient getMongoClient() {
        return this._mongoClient;
    }

    public String getDBName() {
        return this.dbName;
    }

    private void initMongo() {
        try {
            log.info("get mongodb connection...");
            MongoClientOptions options = new MongoClientOptions.Builder()
                .socketKeepAlive(true) // 是否保持长链接
                .connectTimeout(30000)  // 链接超时时间
                .socketTimeout(15000)   // read数据超时时间
                .readPreference(ReadPreference.nearest())
                //.connectionsPerHost(1000) // 每个地址最大请求数
                .maxWaitTime(1000 * 60 * 1) // 长链接的最大等待时间
                .threadsAllowedToBlockForConnectionMultiplier(50)
                .writeConcern(WriteConcern.NORMAL)
                .build();

            _mongoClient = new MongoClient(new ServerAddress("localhost"), options);
            // test for mongodb connection(by calling getCollectionNames on DB):
            Set<String> collNames = _mongoClient.getDB(dbName).getCollectionNames();
            log.info("collections: {}", collNames.toString());
            log.info("get mongodb connection succeeded.");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new IllegalStateException("connect to mongodb failed.");
        }

        // add mongodb close hook on service shutting down.
        Runtime.getRuntime().addShutdownHook( new Thread() {
                @Override
                public void run() {
                    log.info("close mongodb client...");
                    try {
                        _mongoClient.close();
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            });
    }

}

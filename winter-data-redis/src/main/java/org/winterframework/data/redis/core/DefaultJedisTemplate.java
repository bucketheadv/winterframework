package org.winterframework.data.redis.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.winterframework.data.redis.commands.JedisCallback;
import org.winterframework.data.redis.commands.JedisMultiCallback;
import org.winterframework.data.redis.commands.JedisPipelineCallback;
import redis.clients.jedis.*;
import redis.clients.jedis.Module;
import redis.clients.jedis.args.*;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.util.KeyValue;
import redis.clients.jedis.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sven
 * Created on 2022/1/14 11:04 下午
 */
@Slf4j
public class DefaultJedisTemplate implements JedisTemplate {
    private final JedisPool masterPool;

    private final List<JedisPool> slavePools;

    private final String name;

    public DefaultJedisTemplate(String name, JedisPool masterPool, List<JedisPool> slavePools) {
        this.name = name;
        this.masterPool = masterPool;
        this.slavePools = slavePools;
    }

    private <T> T tryGetResource(JedisCallback<T> callback) {
        return tryGetResource(callback, false);
    }

    private <T> T tryGetResource(JedisCallback<T> callback, boolean slave) {
        JedisPool jedisPool = masterPool;
        if (slave && CollectionUtil.isNotEmpty(slavePools)) {
            int n = RandomUtil.randomInt(slavePools.size());
            jedisPool = slavePools.get(n);
        }
        try (Jedis jedis = jedisPool.getResource()){
            return callback.apply(jedis);
        } catch (Exception e) {
            log.error("tryGetResource error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        log.info("[{}] shutdown, bye.", name);
        masterPool.close();
        if (CollectionUtil.isNotEmpty(slavePools)) {
            slavePools.forEach(Pool::close);
        }
    }

    @Override
    public byte[] aclWhoAmIBinary() {
        return tryGetResource(Jedis::aclWhoAmIBinary);
    }

    @Override
    public byte[] aclGenPassBinary() {
        return tryGetResource(Jedis::aclGenPassBinary);
    }

    @Override
    public byte[] aclGenPassBinary(int bits) {
        return tryGetResource(jedis -> jedis.aclGenPassBinary(bits));
    }

    @Override
    public List<byte[]> aclListBinary() {
        return tryGetResource(Jedis::aclListBinary);
    }

    @Override
    public List<byte[]> aclUsersBinary() {
        return tryGetResource(Jedis::aclUsersBinary);
    }

    @Override
    public AccessControlUser aclGetUser(byte[] name) {
        return tryGetResource(jedis -> jedis.aclGetUser(name));
    }

    @Override
    public String aclSetUser(byte[] name) {
        return tryGetResource(jedis -> jedis.aclSetUser(name));
    }

    @Override
    public String aclSetUser(byte[] name, byte[]... keys) {
        return tryGetResource(jedis -> jedis.aclSetUser(name, keys));
    }

    @Override
    public long aclDelUser(byte[] name) {
        return tryGetResource(jedis -> jedis.aclDelUser(name));
    }

    @Override
    public long aclDelUser(byte[] name, byte[]... names) {
        return tryGetResource(jedis -> jedis.aclDelUser(name, names));
    }

    @Override
    public List<byte[]> aclCatBinary() {
        return tryGetResource(Jedis::aclCatBinary);
    }

    @Override
    public List<byte[]> aclCat(byte[] category) {
        return tryGetResource(jedis -> jedis.aclCat(category));
    }

    @Override
    public List<byte[]> aclLogBinary() {
        return tryGetResource(Jedis::aclLogBinary);
    }

    @Override
    public List<byte[]> aclLogBinary(int limit) {
        return tryGetResource(jedis -> jedis.aclLogBinary(limit));
    }

    @Override
    public List<Object> role() {
        return tryGetResource(Jedis::role);
    }

    @Override
    public List<String> objectHelp() {
        return tryGetResource(Jedis::objectHelp);
    }

    @Override
    public String memoryDoctor() {
        return tryGetResource(Jedis::memoryDoctor);
    }

    @Override
    public String aclWhoAmI() {
        return tryGetResource(Jedis::aclWhoAmI);
    }

    @Override
    public String aclGenPass() {
        return tryGetResource(Jedis::aclGenPass);
    }

    @Override
    public String aclGenPass(int bits) {
        return tryGetResource(jedis -> jedis.aclGenPass(bits));
    }

    @Override
    public List<String> aclList() {
        return tryGetResource(Jedis::aclList);
    }

    @Override
    public List<String> aclUsers() {
        return tryGetResource(Jedis::aclUsers);
    }

    @Override
    public AccessControlUser aclGetUser(String name) {
        return tryGetResource(jedis -> jedis.aclGetUser(name));
    }

    @Override
    public String aclSetUser(String name) {
        return tryGetResource(jedis -> jedis.aclSetUser(name));
    }

    @Override
    public String aclSetUser(String name, String... params) {
        return tryGetResource(jedis -> jedis.aclSetUser(name, params));
    }

    @Override
    public long aclDelUser(String name) {
        return tryGetResource(jedis -> jedis.aclDelUser(name));
    }

    @Override
    public long aclDelUser(String name, String... names) {
        return tryGetResource(jedis -> jedis.aclDelUser(name, names));
    }

    @Override
    public List<String> aclCat() {
        return tryGetResource(Jedis::aclCat);
    }

    @Override
    public List<String> aclCat(String category) {
        return tryGetResource(jedis -> jedis.aclCat(category));
    }

    @Override
    public List<AccessControlLogEntry> aclLog() {
        return tryGetResource(Jedis::aclLog);
    }

    @Override
    public List<AccessControlLogEntry> aclLog(int limit) {
        return tryGetResource(jedis -> jedis.aclLog(limit));
    }

    @Override
    public String aclLogReset() {
        return tryGetResource(Jedis::aclLogReset);
    }

    @Override
    public String aclLoad() {
        return tryGetResource(Jedis::aclLoad);
    }

    @Override
    public String aclSave() {
        return tryGetResource(Jedis::aclSave);
    }

    @Override
    public String aclDryRun(String username, String command, String... args) {
        return tryGetResource(jedis -> jedis.aclDryRun(username, command, args));
    }

    @Override
    public String aclDryRun(String username, CommandArguments commandArgs) {
        return tryGetResource(jedis -> jedis.aclDryRun(username, commandArgs));
    }

    @Override
    public byte[] aclDryRunBinary(byte[] username, byte[] command, byte[]... args) {
        return tryGetResource(jedis -> jedis.aclDryRunBinary(username, command, args));
    }

    @Override
    public byte[] aclDryRunBinary(byte[] username, CommandArguments commandArgs) {
        return tryGetResource(jedis -> jedis.aclDryRunBinary(username, commandArgs));
    }

    @Override
    public String clientKill(byte[] ipPort) {
        return tryGetResource(jedis -> jedis.clientKill(ipPort));
    }

    @Override
    public byte[] clientGetnameBinary() {
        return tryGetResource(Jedis::clientGetnameBinary);
    }

    @Override
    public byte[] clientListBinary() {
        return tryGetResource(Jedis::clientListBinary);
    }

    @Override
    public byte[] clientListBinary(ClientType clientType) {
        return tryGetResource(jedis -> jedis.clientListBinary(clientType));
    }

    @Override
    public byte[] clientListBinary(long... clientIds) {
        return tryGetResource(jedis -> jedis.clientListBinary(clientIds));
    }

    @Override
    public byte[] clientInfoBinary() {
        return tryGetResource(Jedis::clientInfoBinary);
    }

    @Override
    public String clientSetname(byte[] name) {
        return tryGetResource(jedis -> jedis.clientSetname(name));
    }

    @Override
    public String clientKill(String ipPort) {
        return tryGetResource(jedis -> jedis.clientKill(ipPort));
    }

    @Override
    public String clientKill(String ip, int port) {
        return tryGetResource(jedis -> jedis.clientKill(ip, port));
    }

    @Override
    public long clientKill(ClientKillParams clientKillParams) {
        return tryGetResource(jedis -> jedis.clientKill(clientKillParams));
    }

    @Override
    public String clientGetname() {
        return tryGetResource(Jedis::clientGetname);
    }

    @Override
    public String clientList() {
        return tryGetResource(Jedis::clientList);
    }

    @Override
    public String clientList(ClientType clientType) {
        return tryGetResource(jedis -> jedis.clientList(clientType));
    }

    @Override
    public String clientList(long... clientIds) {
        return tryGetResource(jedis -> jedis.clientList(clientIds));
    }

    @Override
    public String clientInfo() {
        return tryGetResource(Jedis::clientInfo);
    }

    @Override
    public String clientSetname(String name) {
        return tryGetResource(jedis -> jedis.clientSetname(name));
    }

    @Override
    public long clientId() {
        return tryGetResource(Jedis::clientId);
    }

    @Override
    public long clientUnblock(long clientId) {
        return tryGetResource(jedis -> jedis.clientUnblock(clientId));
    }

    @Override
    public long clientUnblock(long clientId, UnblockType unblockType) {
        return tryGetResource(jedis -> jedis.clientUnblock(clientId, unblockType));
    }

    @Override
    public String clientPause(long timeout) {
        return tryGetResource(jedis -> jedis.clientPause(timeout));
    }

    @Override
    public String clientPause(long timeout, ClientPauseMode clientPauseMode) {
        return tryGetResource(jedis -> jedis.clientPause(timeout, clientPauseMode));
    }

    @Override
    public String clientNoEvictOn() {
        return tryGetResource(Jedis::clientNoEvictOn);
    }

    @Override
    public String clientNoEvictOff() {
        return tryGetResource(Jedis::clientNoEvictOff);
    }

    @Override
    public String asking() {
        return tryGetResource(Jedis::asking);
    }

    @Override
    public String readonly() {
        return tryGetResource(Jedis::readonly);
    }

    @Override
    public String readwrite() {
        return tryGetResource(Jedis::readwrite);
    }

    @Override
    public String clusterNodes() {
        return tryGetResource(Jedis::clusterNodes);
    }

    @Override
    public List<String> clusterReplicas(String nodeId) {
        return tryGetResource(jedis -> jedis.clusterReplicas(nodeId));
    }

    @Override
    public String clusterMeet(String ip, int port) {
        return tryGetResource(jedis -> jedis.clusterMeet(ip, port));
    }

    @Override
    public String clusterAddSlots(int... slots) {
        return tryGetResource(jedis -> jedis.clusterAddSlots(slots));
    }

    @Override
    public String clusterDelSlots(int... slots) {
        return tryGetResource(jedis -> jedis.clusterDelSlots(slots));
    }

    @Override
    public String clusterInfo() {
        return tryGetResource(Jedis::clusterInfo);
    }

    @Override
    public List<String> clusterGetKeysInSlot(int slot, int count) {
        return tryGetResource(jedis -> jedis.clusterGetKeysInSlot(slot, count));
    }

    @Override
    public List<byte[]> clusterGetKeysInSlotBinary(int slot, int count) {
        return tryGetResource(jedis -> jedis.clusterGetKeysInSlotBinary(slot, count));
    }

    @Override
    public String clusterSetSlotNode(int slot, String nodeId) {
        return tryGetResource(jedis -> jedis.clusterSetSlotNode(slot, nodeId));
    }

    @Override
    public String clusterSetSlotMigrating(int slot, String nodeId) {
        return tryGetResource(jedis -> jedis.clusterSetSlotMigrating(slot, nodeId));
    }

    @Override
    public String clusterSetSlotImporting(int slot, String nodeId) {
        return tryGetResource(jedis -> jedis.clusterSetSlotImporting(slot, nodeId));
    }

    @Override
    public String clusterSetSlotStable(int slot) {
        return tryGetResource(jedis -> jedis.clusterSetSlotStable(slot));
    }

    @Override
    public String clusterForget(String nodeId) {
        return tryGetResource(jedis -> jedis.clusterForget(nodeId));
    }

    @Override
    public String clusterFlushSlots() {
        return tryGetResource(Jedis::clusterFlushSlots);
    }

    @Override
    public long clusterKeySlot(String key) {
        return tryGetResource(jedis -> jedis.clusterKeySlot(key));
    }

    @Override
    public long clusterCountFailureReports(String nodeId) {
        return tryGetResource(jedis -> jedis.clusterCountFailureReports(nodeId));
    }

    @Override
    public long clusterCountKeysInSlot(int slot) {
        return tryGetResource(jedis -> jedis.clusterCountKeysInSlot(slot));
    }

    @Override
    public String clusterSaveConfig() {
        return tryGetResource(Jedis::clusterSaveConfig);
    }

    @Override
    public String clusterSetConfigEpoch(long configEpoch) {
        return tryGetResource(jedis -> jedis.clusterSetConfigEpoch(configEpoch));
    }

    @Override
    public String clusterBumpEpoch() {
        return tryGetResource(Jedis::clusterBumpEpoch);
    }

    @Override
    public String clusterReplicate(String nodeId) {
        return tryGetResource(jedis -> jedis.clusterReplicate(nodeId));
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<String> clusterSlaves(String nodeId) {
        return tryGetResource(jedis -> jedis.clusterSlaves(nodeId));
    }

    @Override
    public String clusterFailover() {
        return tryGetResource(Jedis::clusterFailover);
    }

    @Override
    public String clusterFailover(ClusterFailoverOption clusterFailoverOption) {
        return tryGetResource(jedis -> jedis.clusterFailover(clusterFailoverOption));
    }

    @Override
    public List<Object> clusterSlots() {
        return tryGetResource(Jedis::clusterSlots);
    }

    @Override
    public String clusterReset() {
        return tryGetResource(Jedis::clusterReset);
    }

    @Override
    public String clusterReset(ClusterResetType clusterResetType) {
        return tryGetResource(jedis -> jedis.clusterReset(clusterResetType));
    }

    @Override
    public String clusterMyId() {
        return tryGetResource(Jedis::clusterMyId);
    }

    @Override
    public List<Map<String, Object>> clusterLinks() {
        return tryGetResource(Jedis::clusterLinks);
    }

    @Override
    public String clusterAddSlotsRange(int... ranges) {
        return tryGetResource(jedis -> jedis.clusterAddSlotsRange(ranges));
    }

    @Override
    public String clusterDelSlotsRange(int... ranges) {
        return tryGetResource(jedis -> jedis.clusterDelSlotsRange(ranges));
    }

    @Override
    public List<String> configGet(String pattern) {
        return tryGetResource(jedis -> jedis.configGet(pattern));
    }

    @Override
    public List<String> configGet(String... patterns) {
        return tryGetResource(jedis -> jedis.configGet(patterns));
    }

    @Override
    public List<byte[]> configGet(byte[] pattern) {
        return tryGetResource(jedis -> jedis.configGet(pattern));
    }

    @Override
    public List<byte[]> configGet(byte[]... patterns) {
        return tryGetResource(jedis -> jedis.configGet(patterns));
    }

    @Override
    public String configSet(String parameter, String value) {
        return tryGetResource(jedis -> jedis.configSet(parameter, value));
    }

    @Override
    public String configSet(String... parameterValues) {
        return tryGetResource(jedis -> jedis.configSet(parameterValues));
    }

    @Override
    public String configSet(byte[] parameter, byte[] value) {
        return tryGetResource(jedis -> jedis.configSet(parameter, value));
    }

    @Override
    public String configSet(byte[]... parameterValues) {
        return tryGetResource(jedis -> jedis.configSet(parameterValues));
    }

    @Override
    public String configResetStat() {
        return tryGetResource(Jedis::configResetStat);
    }

    @Override
    public String configRewrite() {
        return tryGetResource(Jedis::configRewrite);
    }

    @Override
    public List<Object> roleBinary() {
        return tryGetResource(Jedis::roleBinary);
    }

    @Override
    public List<byte[]> objectHelpBinary() {
        return tryGetResource(Jedis::objectHelpBinary);
    }

    @Override
    public byte[] memoryDoctorBinary() {
        return tryGetResource(Jedis::memoryDoctorBinary);
    }

    @Override
    public String select(int index) {
        return tryGetResource(jedis -> jedis.select(index));
    }

    @Override
    public long dbSize() {
        return tryGetResource(Jedis::dbSize);
    }

    @Override
    public String flushDB(FlushMode flushMode) {
        return tryGetResource(jedis -> jedis.flushDB(flushMode));
    }

    @Override
    public String swapDB(int index1, int index2) {
        return tryGetResource(jedis -> jedis.swapDB(index1, index2));
    }

    @Override
    public long move(String key, int dbIndex) {
        return tryGetResource(jedis -> jedis.move(key, dbIndex));
    }

    @Override
    public long move(byte[] key, int dbIndex) {
        return tryGetResource(jedis -> jedis.move(key, dbIndex));
    }

    @Override
    public boolean copy(String srcKey, String dstKey, int db, boolean replace) {
        return tryGetResource(jedis -> jedis.copy(srcKey, dstKey, db, replace));
    }

    @Override
    public boolean copy(byte[] srcKey, byte[] dstKey, int db, boolean replace) {
        return tryGetResource(jedis -> jedis.copy(srcKey, dstKey, db, replace));
    }

    @Override
    public String migrate(String host, int port, byte[] key, int destinationDb, int timeout) {
        return tryGetResource(jedis -> jedis.migrate(host, port, key, destinationDb, timeout));
    }

    @Override
    public String migrate(String host, int port, int destinationDB, int timeout, MigrateParams params, byte[]... keys) {
        return tryGetResource(jedis -> jedis.migrate(host, port, destinationDB, timeout, params, keys));
    }

    @Override
    public String migrate(String host, int port, String key, int destinationDb, int timeout) {
        return tryGetResource(jedis -> jedis.migrate(host, port, key, destinationDb, timeout));
    }

    @Override
    public String migrate(String host, int port, int destinationDB, int timeout, MigrateParams params, String... keys) {
        return tryGetResource(jedis -> jedis.migrate(host, port, destinationDB, timeout, params, keys));
    }

    @Override
    public String failover() {
        return tryGetResource(Jedis::failover);
    }

    @Override
    public String failover(FailoverParams failoverParams) {
        return tryGetResource(jedis -> jedis.failover(failoverParams));
    }

    @Override
    public String failoverAbort() {
        return tryGetResource(Jedis::failoverAbort);
    }

    @Override
    public long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return tryGetResource(jedis -> jedis.geoadd(key, longitude, latitude, member));
    }

    @Override
    public long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return tryGetResource(jedis -> jedis.geoadd(key, memberCoordinateMap));
    }

    @Override
    public long geoadd(byte[] key, GeoAddParams params, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return tryGetResource(jedis -> jedis.geoadd(key, params, memberCoordinateMap));
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2) {
        return tryGetResource(jedis -> jedis.geodist(key, member1, member2));
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geodist(key, member1, member2, unit));
    }

    @Override
    public List<byte[]> geohash(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.geohash(key, members));
    }

    @Override
    public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.geopos(key, members));
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadius(key, longitude, latitude, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusReadonly(key, longitude, latitude, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadius(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusReadonly(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusByMember(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusByMemberReadonly(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusByMember(key, member, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusByMemberReadonly(key, member, radius, unit, param));
    }

    @Override
    public long georadiusStore(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return tryGetResource(jedis -> jedis.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam));
    }

    @Override
    public long georadiusByMemberStore(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return tryGetResource(jedis -> jedis.georadiusByMemberStore(key, member, radius, unit, param, storeParam));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(byte[] key, GeoCoordinate coord, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, coord, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(byte[] key, byte[] member, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, member, width, height, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(byte[] key, GeoCoordinate coord, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, coord, width, height, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(byte[] key, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearch(key, params));
    }

    @Override
    public long geosearchStore(byte[] dest, byte[] src, byte[] member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, member, radius, unit));
    }

    @Override
    public long geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, coord, radius, unit));
    }

    @Override
    public long geosearchStore(byte[] dest, byte[] src, byte[] member, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, member, width, height, unit));
    }

    @Override
    public long geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, coord, width, height, unit));
    }

    @Override
    public long geosearchStore(byte[] dest, byte[] src, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, params));
    }

    @Override
    public long geosearchStoreStoreDist(byte[] dest, byte[] src, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearchStoreStoreDist(dest, src, params));
    }

    @Override
    public long geoadd(String key, double longitude, double latitude, String member) {
        return tryGetResource(jedis -> jedis.geoadd(key, longitude, latitude, member));
    }

    @Override
    public long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return tryGetResource(jedis -> jedis.geoadd(key, memberCoordinateMap));
    }

    @Override
    public long geoadd(String key, GeoAddParams params, Map<String, GeoCoordinate> memberCoordinateMap) {
        return tryGetResource(jedis -> jedis.geoadd(key, params, memberCoordinateMap));
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        return tryGetResource(jedis -> jedis.geodist(key, member1, member2));
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geodist(key, member1, member2, unit));
    }

    @Override
    public List<String> geohash(String key, String... members) {
        return tryGetResource(jedis -> jedis.geohash(key, members));
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        return tryGetResource(jedis -> jedis.geopos(key, members));
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadius(key, longitude, latitude, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusReadonly(key, longitude, latitude, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadius(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusReadonly(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusByMember(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.georadiusByMemberReadonly(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusByMember(key, member, radius, unit, param));
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return tryGetResource(jedis -> jedis.georadiusByMemberReadonly(key, member, radius, unit, param));
    }

    @Override
    public long georadiusStore(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return tryGetResource(jedis -> jedis.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam));
    }

    @Override
    public long georadiusByMemberStore(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return tryGetResource(jedis -> jedis.georadiusByMemberStore(key, member, radius, unit, param, storeParam));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(String key, String member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, member, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(String key, GeoCoordinate coord, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, coord, radius, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(String key, String member, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, member, width, height, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(String key, GeoCoordinate coord, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearch(key, coord, width, height, unit));
    }

    @Override
    public List<GeoRadiusResponse> geosearch(String key, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearch(key, params));
    }

    @Override
    public long geosearchStore(String dest, String src, String member, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, member, radius, unit));
    }

    @Override
    public long geosearchStore(String dest, String src, GeoCoordinate coord, double radius, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, coord, radius, unit));
    }

    @Override
    public long geosearchStore(String dest, String src, String member, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, member, width, height, unit));
    }

    @Override
    public long geosearchStore(String dest, String src, GeoCoordinate coord, double width, double height, GeoUnit unit) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, coord, width, height, unit));
    }

    @Override
    public long geosearchStore(String dest, String src, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearchStore(dest, src, params));
    }

    @Override
    public long geosearchStoreStoreDist(String dest, String src, GeoSearchParam params) {
        return tryGetResource(jedis -> jedis.geosearchStoreStoreDist(dest, src, params));
    }

    @Override
    public long hset(byte[] key, byte[] field, byte[] value) {
        return tryGetResource(jedis -> jedis.hset(key, field, value));
    }

    @Override
    public long hset(byte[] key, Map<byte[], byte[]> hash) {
        return tryGetResource(jedis -> jedis.hset(key, hash));
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        return tryGetResource(jedis -> jedis.hget(key, field), true);
    }

    @Override
    public long hsetnx(byte[] key, byte[] field, byte[] value) {
        return tryGetResource(jedis -> jedis.hsetnx(key, field, value));
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return tryGetResource(jedis -> jedis.hmset(key, hash));
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        return tryGetResource(jedis -> jedis.hmget(key, fields), true);
    }

    @Override
    public long hincrBy(byte[] key, byte[] field, long value) {
        return tryGetResource(jedis -> jedis.hincrBy(key, field, value));
    }

    @Override
    public double hincrByFloat(byte[] key, byte[] field, double value) {
        return tryGetResource(jedis -> jedis.hincrByFloat(key, field, value));
    }

    @Override
    public boolean hexists(byte[] key, byte[] field) {
        return tryGetResource(jedis -> jedis.hexists(key, field));
    }

    @Override
    public long hdel(byte[] key, byte[]... fields) {
        return tryGetResource(jedis -> jedis.hdel(key, fields));
    }

    @Override
    public long hlen(byte[] key) {
        return tryGetResource(jedis -> jedis.hlen(key), true);
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        return tryGetResource(jedis -> jedis.hkeys(key), true);
    }

    @Override
    public List<byte[]> hvals(byte[] key) {
        return tryGetResource(jedis -> jedis.hvals(key), true);
    }

    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        return tryGetResource(jedis -> jedis.hgetAll(key), true);
    }

    @Override
    public byte[] hrandfield(byte[] key) {
        return tryGetResource(jedis -> jedis.hrandfield(key));
    }

    @Override
    public List<byte[]> hrandfield(byte[] key, long count) {
        return tryGetResource(jedis -> jedis.hrandfield(key, count));
    }

    @Override
    public Map<byte[], byte[]> hrandfieldWithValues(byte[] key, long count) {
        return tryGetResource(jedis -> jedis.hrandfieldWithValues(key, count));
    }

    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return tryGetResource(jedis -> jedis.hscan(key, cursor, params), true);
    }

    @Override
    public long hstrlen(byte[] key, byte[] field) {
        return tryGetResource(jedis -> jedis.hstrlen(key, field));
    }

    @Override
    public long hset(String key, String field, String value) {
        return tryGetResource(jedis -> jedis.hset(key, field, value));
    }

    @Override
    public long hset(String key, Map<String, String> hash) {
        return tryGetResource(jedis -> jedis.hset(key, hash));
    }

    @Override
    public String hget(String key, String field) {
        return tryGetResource(jedis -> jedis.hget(key, field), true);
    }

    @Override
    public long hsetnx(String key, String field, String value) {
        return tryGetResource(jedis -> jedis.hsetnx(key, field, value));
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        return tryGetResource(jedis -> jedis.hmset(key, hash));
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return tryGetResource(jedis -> jedis.hmget(key, fields), true);
    }

    @Override
    public long hincrBy(String key, String field, long value) {
        return tryGetResource(jedis -> jedis.hincrBy(key, field, value));
    }

    @Override
    public double hincrByFloat(String key, String field, double value) {
        return tryGetResource(jedis -> jedis.hincrByFloat(key, field, value));
    }

    @Override
    public boolean hexists(String key, String field) {
        return tryGetResource(jedis -> jedis.hexists(key, field));
    }

    @Override
    public long hdel(String key, String... fields) {
        return tryGetResource(jedis -> jedis.hdel(key, fields));
    }

    @Override
    public long hlen(String key) {
        return tryGetResource(jedis -> jedis.hlen(key), true);
    }

    @Override
    public Set<String> hkeys(String key) {
        return tryGetResource(jedis -> jedis.hkeys(key), true);
    }

    @Override
    public List<String> hvals(String key) {
        return tryGetResource(jedis -> jedis.hvals(key), true);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return tryGetResource(jedis -> jedis.hgetAll(key), true);
    }

    @Override
    public String hrandfield(String key) {
        return tryGetResource(jedis -> jedis.hrandfield(key));
    }

    @Override
    public List<String> hrandfield(String key, long count) {
        return tryGetResource(jedis -> jedis.hrandfield(key, count));
    }

    @Override
    public Map<String, String> hrandfieldWithValues(String key, long count) {
        return tryGetResource(jedis -> jedis.hrandfieldWithValues(key, count));
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.hscan(key, cursor, scanParams), true);
    }

    @Override
    public long hstrlen(String key, String field) {
        return tryGetResource(jedis -> jedis.hstrlen(key, field));
    }

    @Override
    public long pfadd(byte[] key, byte[]... elements) {
        return tryGetResource(jedis -> jedis.pfadd(key, elements));
    }

    @Override
    public String pfmerge(byte[] destKey, byte[]... sourceKeys) {
        return tryGetResource(jedis -> jedis.pfmerge(destKey, sourceKeys));
    }

    @Override
    public long pfcount(byte[] key) {
        return tryGetResource(jedis -> jedis.pfcount(key));
    }

    @Override
    public long pfcount(byte[]... keys) {
        return tryGetResource(jedis -> jedis.pfcount(keys));
    }

    @Override
    public long pfadd(String key, String... elements) {
        return tryGetResource(jedis -> jedis.pfadd(key, elements));
    }

    @Override
    public String pfmerge(String destKey, String... sourceKeys) {
        return tryGetResource(jedis -> jedis.pfmerge(destKey, sourceKeys));
    }

    @Override
    public long pfcount(String key) {
        return tryGetResource(jedis -> jedis.pfcount(key));
    }

    @Override
    public long pfcount(String... keys) {
        return tryGetResource(jedis -> jedis.pfcount(keys));
    }

    @Override
    public boolean exists(byte[] key) {
        return tryGetResource(jedis -> jedis.exists(key));
    }

    @Override
    public long exists(byte[]... keys) {
        return tryGetResource(jedis -> jedis.exists(keys));
    }

    @Override
    public long persist(byte[] key) {
        return tryGetResource(jedis -> jedis.persist(key));
    }

    @Override
    public String type(byte[] key) {
        return tryGetResource(jedis -> jedis.type(key));
    }

    @Override
    public byte[] dump(byte[] key) {
        return tryGetResource(jedis -> jedis.dump(key));
    }

    @Override
    public String restore(byte[] key, long ttl, byte[] serializedValue) {
        return tryGetResource(jedis -> jedis.restore(key, ttl, serializedValue));
    }

    @Override
    public String restore(byte[] key, long ttl, byte[] serializedValue, RestoreParams restoreParams) {
        return tryGetResource(jedis -> jedis.restore(key, ttl, serializedValue, restoreParams));
    }

    @Override
    public long expire(byte[] key, long seconds) {
        return tryGetResource(jedis -> jedis.expire(key, seconds));
    }

    @Override
    public long expire(byte[] key, long seconds, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.expire(key, seconds, expiryOption));
    }

    @Override
    public long pexpire(byte[] key, long milliseconds) {
        return tryGetResource(jedis -> jedis.pexpire(key, milliseconds));
    }

    @Override
    public long pexpire(byte[] key, long milliseconds, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.pexpire(key, milliseconds, expiryOption));
    }

    @Override
    public long expireTime(byte[] key) {
        return tryGetResource(jedis -> jedis.expireTime(key));
    }

    @Override
    public long pexpireTime(byte[] key) {
        return tryGetResource(jedis -> jedis.pexpireTime(key));
    }

    @Override
    public long expireAt(byte[] key, long unixTime) {
        return tryGetResource(jedis -> jedis.expireAt(key, unixTime));
    }

    @Override
    public long expireAt(byte[] key, long unixTime, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.expireAt(key, unixTime, expiryOption));
    }

    @Override
    public long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return tryGetResource(jedis -> jedis.pexpireAt(key, millisecondsTimestamp));
    }

    @Override
    public long pexpireAt(byte[] key, long millisecondsTimestamp, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.pexpireAt(key, millisecondsTimestamp, expiryOption));
    }

    @Override
    public long ttl(byte[] key) {
        return tryGetResource(jedis -> jedis.ttl(key));
    }

    @Override
    public long pttl(byte[] key) {
        return tryGetResource(jedis -> jedis.pttl(key));
    }

    @Override
    public long touch(byte[] key) {
        return tryGetResource(jedis -> jedis.touch(key));
    }

    @Override
    public long touch(byte[]... keys) {
        return tryGetResource(jedis -> jedis.touch(keys));
    }

    @Override
    public List<byte[]> sort(byte[] key) {
        return tryGetResource(jedis -> jedis.sort(key));
    }

    @Override
    public List<byte[]> sort(byte[] key, SortingParams sortingParams) {
        return tryGetResource(jedis -> jedis.sort(key, sortingParams));
    }

    @Override
    public long del(byte[] key) {
        return tryGetResource(jedis -> jedis.del(key));
    }

    @Override
    public long del(byte[]... keys) {
        return tryGetResource(jedis -> jedis.del(keys));
    }

    @Override
    public long unlink(byte[] key) {
        return tryGetResource(jedis -> jedis.unlink(key));
    }

    @Override
    public long unlink(byte[]... keys) {
        return tryGetResource(jedis -> jedis.unlink(keys));
    }

    @Override
    public boolean copy(byte[] srcKey, byte[] dstKey, boolean replace) {
        return tryGetResource(jedis -> jedis.copy(srcKey, dstKey, replace));
    }

    @Override
    public String rename(byte[] oldkey, byte[] newkey) {
        return tryGetResource(jedis -> jedis.rename(oldkey, newkey));
    }

    @Override
    public long renamenx(byte[] oldkey, byte[] newkey) {
        return tryGetResource(jedis -> jedis.renamenx(oldkey, newkey));
    }

    @Override
    public long sort(byte[] key, SortingParams sortingParams, byte[] dstkey) {
        return tryGetResource(jedis -> jedis.sort(key, sortingParams, dstkey));
    }

    @Override
    public long sort(byte[] key, byte[] dstkey) {
        return tryGetResource(jedis -> jedis.sort(key, dstkey));
    }

    @Override
    public List<byte[]> sortReadonly(byte[] key, SortingParams sortingParams) {
        return tryGetResource(jedis -> jedis.sortReadonly(key, sortingParams));
    }

    @Override
    public Long memoryUsage(byte[] key) {
        return tryGetResource(jedis -> jedis.memoryUsage(key));
    }

    @Override
    public Long memoryUsage(byte[] key, int samples) {
        return tryGetResource(jedis -> jedis.memoryUsage(key, samples));
    }

    @Override
    public Long objectRefcount(byte[] key) {
        return tryGetResource(jedis -> jedis.objectRefcount(key));
    }

    @Override
    public byte[] objectEncoding(byte[] key) {
        return tryGetResource(jedis -> jedis.objectEncoding(key));
    }

    @Override
    public Long objectIdletime(byte[] key) {
        return tryGetResource(jedis -> jedis.objectIdletime(key));
    }

    @Override
    public Long objectFreq(byte[] key) {
        return tryGetResource(jedis -> jedis.objectFreq(key));
    }

    @Override
    public String migrate(String host, int port, byte[] key, int timeout) {
        return tryGetResource(jedis -> jedis.migrate(host, port, key, timeout));
    }

    @Override
    public String migrate(String host, int port, int timeout, MigrateParams params, byte[]... keys) {
        return tryGetResource(jedis -> jedis.migrate(host, port, timeout, params, keys));
    }

    @Override
    public Set<byte[]> keys(byte[] pattern) {
        return tryGetResource(jedis -> jedis.keys(pattern), true);
    }

    @Override
    public ScanResult<byte[]> scan(byte[] cursor) {
        return tryGetResource(jedis -> jedis.scan(cursor), true);
    }

    @Override
    public ScanResult<byte[]> scan(byte[] cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.scan(cursor, scanParams), true);
    }

    @Override
    public ScanResult<byte[]> scan(byte[] cursor, ScanParams scanParams, byte[] type) {
        return tryGetResource(jedis -> jedis.scan(cursor, scanParams, type), true);
    }

    @Override
    public byte[] randomBinaryKey() {
        return tryGetResource(Jedis::randomBinaryKey);
    }

    @Override
    public boolean exists(String key) {
        return tryGetResource(jedis -> jedis.exists(key));
    }

    @Override
    public long exists(String... keys) {
        return tryGetResource(jedis -> jedis.exists(keys));
    }

    @Override
    public long persist(String key) {
        return tryGetResource(jedis -> jedis.persist(key));
    }

    @Override
    public String type(String key) {
        return tryGetResource(jedis -> jedis.type(key));
    }

    @Override
    public byte[] dump(String key) {
        return tryGetResource(jedis -> jedis.dump(key));
    }

    @Override
    public String restore(String key, long ttl, byte[] serializedValue) {
        return tryGetResource(jedis -> jedis.restore(key, ttl, serializedValue));
    }

    @Override
    public String restore(String key, long ttl, byte[] serializedValue, RestoreParams params) {
        return tryGetResource(jedis -> jedis.restore(key, ttl, serializedValue, params));
    }

    @Override
    public long expire(String key, long seconds) {
        return tryGetResource(jedis -> jedis.expire(key, seconds));
    }

    @Override
    public long expire(String key, long seconds, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.expire(key, seconds, expiryOption));
    }

    @Override
    public long pexpire(String key, long milliseconds) {
        return tryGetResource(jedis -> jedis.pexpire(key, milliseconds));
    }

    @Override
    public long pexpire(String key, long milliseconds, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.pexpire(key, milliseconds, expiryOption));
    }

    @Override
    public long expireTime(String key) {
        return tryGetResource(jedis -> jedis.expireTime(key));
    }

    @Override
    public long pexpireTime(String key) {
        return tryGetResource(jedis -> jedis.pexpireTime(key));
    }

    @Override
    public long expireAt(String key, long unixTime) {
        return tryGetResource(jedis -> jedis.expireAt(key, unixTime));
    }

    @Override
    public long expireAt(String key, long unixTime, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.expireAt(key, unixTime, expiryOption));
    }

    @Override
    public long pexpireAt(String key, long millisecondsTimestamp) {
        return tryGetResource(jedis -> jedis.pexpireAt(key, millisecondsTimestamp));
    }

    @Override
    public long pexpireAt(String key, long millisecondsTimestamp, ExpiryOption expiryOption) {
        return tryGetResource(jedis -> jedis.pexpireAt(key, millisecondsTimestamp, expiryOption));
    }

    @Override
    public long ttl(String key) {
        return tryGetResource(jedis -> jedis.ttl(key));
    }

    @Override
    public long pttl(String key) {
        return tryGetResource(jedis -> jedis.pttl(key));
    }

    @Override
    public long touch(String key) {
        return tryGetResource(jedis -> jedis.touch(key));
    }

    @Override
    public long touch(String... keys) {
        return tryGetResource(jedis -> jedis.touch(keys));
    }

    @Override
    public List<String> sort(String key) {
        return tryGetResource(jedis -> jedis.sort(key));
    }

    @Override
    public long sort(String key, String dstkey) {
        return tryGetResource(jedis -> jedis.sort(key, dstkey));
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParams) {
        return tryGetResource(jedis -> jedis.sort(key, sortingParams));
    }

    @Override
    public long sort(String key, SortingParams sortingParams, String dstkey) {
        return tryGetResource(jedis -> jedis.sort(key, sortingParams, dstkey));
    }

    @Override
    public List<String> sortReadonly(String key, SortingParams sortingParams) {
        return tryGetResource(jedis -> jedis.sortReadonly(key, sortingParams));
    }

    @Override
    public long del(String key) {
        return tryGetResource(jedis -> jedis.del(key));
    }

    @Override
    public long del(String... keys) {
        return tryGetResource(jedis -> jedis.del(keys));
    }

    @Override
    public long unlink(String key) {
        return tryGetResource(jedis -> jedis.unlink(key));
    }

    @Override
    public long unlink(String... keys) {
        return tryGetResource(jedis -> jedis.unlink(keys));
    }

    @Override
    public boolean copy(String srcKey, String dstKey, boolean replace) {
        return tryGetResource(jedis -> jedis.copy(srcKey, dstKey, replace));
    }

    @Override
    public String rename(String oldkey, String newkey) {
        return tryGetResource(jedis -> jedis.rename(oldkey, newkey));
    }

    @Override
    public long renamenx(String oldkey, String newkey) {
        return tryGetResource(jedis -> jedis.renamenx(oldkey, newkey));
    }

    @Override
    public Long memoryUsage(String key) {
        return tryGetResource(jedis -> jedis.memoryUsage(key));
    }

    @Override
    public Long memoryUsage(String key, int samples) {
        return tryGetResource(jedis -> jedis.memoryUsage(key, samples));
    }

    @Override
    public String memoryPurge() {
        return tryGetResource(Jedis::memoryPurge);
    }

    @Override
    public Map<String, Object> memoryStats() {
        return tryGetResource(Jedis::memoryStats);
    }

    @Override
    public Long objectRefcount(String key) {
        return tryGetResource(jedis -> jedis.objectRefcount(key));
    }

    @Override
    public String objectEncoding(String key) {
        return tryGetResource(jedis -> jedis.objectEncoding(key));
    }

    @Override
    public Long objectIdletime(String key) {
        return tryGetResource(jedis -> jedis.objectIdletime(key));
    }

    @Override
    public Long objectFreq(String key) {
        return tryGetResource(jedis -> jedis.objectFreq(key));
    }

    @Override
    public String migrate(String host, int port, String key, int timeout) {
        return tryGetResource(jedis -> jedis.migrate(host, port, key, timeout));
    }

    @Override
    public String migrate(String host, int port, int timeout, MigrateParams params, String... keys) {
        return tryGetResource(jedis -> jedis.migrate(host, port, timeout, params, keys));
    }

    @Override
    public Set<String> keys(String pattern) {
        return tryGetResource(jedis -> jedis.keys(pattern), true);
    }

    @Override
    public ScanResult<String> scan(String cursor) {
        return tryGetResource(jedis -> jedis.scan(cursor), true);
    }

    @Override
    public ScanResult<String> scan(String cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.scan(cursor, scanParams), true);
    }

    @Override
    public ScanResult<String> scan(String cursor, ScanParams scanParams, String type) {
        return tryGetResource(jedis -> jedis.scan(cursor, scanParams, type), true);
    }

    @Override
    public String randomKey() {
        return tryGetResource(Jedis::randomKey);
    }

    @Override
    public long rpush(byte[] key, byte[]... strings) {
        return tryGetResource(jedis -> jedis.rpush(key, strings));
    }

    @Override
    public long lpush(byte[] key, byte[]... strings) {
        return tryGetResource(jedis -> jedis.lpush(key, strings));
    }

    @Override
    public long llen(byte[] key) {
        return tryGetResource(jedis -> jedis.llen(key), true);
    }

    @Override
    public List<byte[]> lrange(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.lrange(key, start, stop), true);
    }

    @Override
    public String ltrim(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.ltrim(key, start, stop));
    }

    @Override
    public byte[] lindex(byte[] key, long index) {
        return tryGetResource(jedis -> jedis.lindex(key, index), true);
    }

    @Override
    public String lset(byte[] key, long index, byte[] value) {
        return tryGetResource(jedis -> jedis.lset(key, index, value));
    }

    @Override
    public long lrem(byte[] key, long count, byte[] value) {
        return tryGetResource(jedis -> jedis.lrem(key, count, value));
    }

    @Override
    public byte[] lpop(byte[] key) {
        return tryGetResource(jedis -> jedis.lpop(key));
    }

    @Override
    public List<byte[]> lpop(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.lpop(key, count));
    }

    @Override
    public Long lpos(byte[] key, byte[] element) {
        return tryGetResource(jedis -> jedis.lpos(key, element), true);
    }

    @Override
    public Long lpos(byte[] key, byte[] element, LPosParams lPosParams) {
        return tryGetResource(jedis -> jedis.lpos(key, element, lPosParams), true);
    }

    @Override
    public List<Long> lpos(byte[] key, byte[] element, LPosParams lPosParams, long count) {
        return tryGetResource(jedis -> jedis.lpos(key, element, lPosParams, count), true);
    }

    @Override
    public byte[] rpop(byte[] key) {
        return tryGetResource(jedis -> jedis.rpop(key));
    }

    @Override
    public List<byte[]> rpop(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.rpop(key, count));
    }

    @Override
    public long linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value) {
        return tryGetResource(jedis -> jedis.linsert(key, where, pivot, value));
    }

    @Override
    public long lpushx(byte[] key, byte[]... string) {
        return tryGetResource(jedis -> jedis.lpushx(key, string));
    }

    @Override
    public long rpushx(byte[] key, byte[]... string) {
        return tryGetResource(jedis -> jedis.rpushx(key, string));
    }

    @Override
    public List<byte[]> blpop(int timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.blpop(timeout, keys));
    }

    @Override
    public List<byte[]> blpop(double timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.blpop(timeout, keys));
    }

    @Override
    public List<byte[]> brpop(int timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.brpop(timeout, keys));
    }

    @Override
    public List<byte[]> brpop(double timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.brpop(timeout, keys));
    }

    @Override
    public byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
        return tryGetResource(jedis -> jedis.rpoplpush(srckey, dstkey));
    }

    @Override
    public byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
        return tryGetResource(jedis -> jedis.brpoplpush(source, destination, timeout));
    }

    @Override
    public byte[] lmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to) {
        return tryGetResource(jedis -> jedis.lmove(srcKey, dstKey, from, to));
    }

    @Override
    public byte[] blmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to, double timeout) {
        return tryGetResource(jedis -> jedis.blmove(srcKey, dstKey, from, to, timeout));
    }

    @Override
    public KeyValue<byte[], List<byte[]>> lmpop(ListDirection direction, byte[]... keys) {
        return tryGetResource(jedis -> jedis.lmpop(direction, keys));
    }

    @Override
    public KeyValue<byte[], List<byte[]>> lmpop(ListDirection direction, int count, byte[]... keys) {
        return tryGetResource(jedis -> jedis.lmpop(direction, count, keys));
    }

    @Override
    public KeyValue<byte[], List<byte[]>> blmpop(long timeout, ListDirection direction, byte[]... keys) {
        return tryGetResource(jedis -> jedis.blmpop(timeout, direction, keys));
    }

    @Override
    public KeyValue<byte[], List<byte[]>> blmpop(long timeout, ListDirection direction, int count, byte[]... keys) {
        return tryGetResource(jedis -> jedis.blmpop(timeout, direction, count, keys));
    }

    @Override
    public long rpush(String key, String... strings) {
        return tryGetResource(jedis -> jedis.rpush(key, strings));
    }

    @Override
    public long lpush(String key, String... strings) {
        return tryGetResource(jedis -> jedis.lpush(key, strings));
    }

    @Override
    public long llen(String key) {
        return tryGetResource(jedis -> jedis.llen(key), true);
    }

    @Override
    public List<String> lrange(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.lrange(key, start, stop), true);
    }

    @Override
    public String ltrim(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.ltrim(key, start, stop));
    }

    @Override
    public String lindex(String key, long index) {
        return tryGetResource(jedis -> jedis.lindex(key, index), true);
    }

    @Override
    public String lset(String key, long index, String value) {
        return tryGetResource(jedis -> jedis.lset(key, index, value));
    }

    @Override
    public long lrem(String key, long count, String value) {
        return tryGetResource(jedis -> jedis.lrem(key, count, value));
    }

    @Override
    public String lpop(String key) {
        return tryGetResource(jedis -> jedis.lpop(key));
    }

    @Override
    public List<String> lpop(String key, int count) {
        return tryGetResource(jedis -> jedis.lpop(key, count));
    }

    @Override
    public Long lpos(String key, String element) {
        return tryGetResource(jedis -> jedis.lpos(key, element), true);
    }

    @Override
    public Long lpos(String key, String element, LPosParams lPosParams) {
        return tryGetResource(jedis -> jedis.lpos(key, element, lPosParams), true);
    }

    @Override
    public List<Long> lpos(String key, String element, LPosParams params, long count) {
        return tryGetResource(jedis -> jedis.lpos(key, element, params, count), true);
    }

    @Override
    public String rpop(String key) {
        return tryGetResource(jedis -> jedis.rpop(key));
    }

    @Override
    public List<String> rpop(String key, int count) {
        return tryGetResource(jedis -> jedis.rpop(key, count));
    }

    @Override
    public long linsert(String key, ListPosition where, String pivot, String value) {
        return tryGetResource(jedis -> jedis.linsert(key, where, pivot, value));
    }

    @Override
    public long lpushx(String key, String... string) {
        return tryGetResource(jedis -> jedis.lpushx(key, string));
    }

    @Override
    public long rpushx(String key, String... string) {
        return tryGetResource(jedis -> jedis.rpushx(key, string));
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        return tryGetResource(jedis -> jedis.blpop(timeout, key));
    }

    @Override
    public KeyedListElement blpop(double timeout, String key) {
        return tryGetResource(jedis -> jedis.blpop(timeout, key));
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        return tryGetResource(jedis -> jedis.brpop(timeout, key));
    }

    @Override
    public KeyedListElement brpop(double timeout, String key) {
        return tryGetResource(jedis -> jedis.brpop(timeout, key));
    }

    @Override
    public List<String> blpop(int timeout, String... keys) {
        return tryGetResource(jedis -> jedis.blpop(timeout, keys));
    }

    @Override
    public KeyedListElement blpop(double timeout, String... keys) {
        return tryGetResource(jedis -> jedis.blpop(timeout, keys));
    }

    @Override
    public List<String> brpop(int timeout, String... keys) {
        return tryGetResource(jedis -> jedis.brpop(timeout, keys));
    }

    @Override
    public KeyedListElement brpop(double timeout, String... keys) {
        return tryGetResource(jedis -> jedis.brpop(timeout, keys));
    }

    @Override
    public String rpoplpush(String srckey, String dstkey) {
        return tryGetResource(jedis -> jedis.rpoplpush(srckey, dstkey));
    }

    @Override
    public String brpoplpush(String source, String destination, int timeout) {
        return tryGetResource(jedis -> jedis.brpoplpush(source, destination, timeout));
    }

    @Override
    public String lmove(String srcKey, String dstKey, ListDirection from, ListDirection to) {
        return tryGetResource(jedis -> jedis.lmove(srcKey, dstKey, from, to));
    }

    @Override
    public String blmove(String srcKey, String dstKey, ListDirection from, ListDirection to, double timeout) {
        return tryGetResource(jedis -> jedis.blmove(srcKey, dstKey, from, to, timeout));
    }

    @Override
    public KeyValue<String, List<String>> lmpop(ListDirection direction, String... keys) {
        return tryGetResource(jedis -> jedis.lmpop(direction, keys));
    }

    @Override
    public KeyValue<String, List<String>> lmpop(ListDirection direction, int count, String... keys) {
        return tryGetResource(jedis -> jedis.lmpop(direction, count, keys));
    }

    @Override
    public KeyValue<String, List<String>> blmpop(long timeout, ListDirection direction, String... keys) {
        return tryGetResource(jedis -> jedis.blmpop(timeout, direction, keys));
    }

    @Override
    public KeyValue<String, List<String>> blmpop(long timeout, ListDirection direction, int count, String... keys) {
        return tryGetResource(jedis -> jedis.blmpop(timeout, direction, count, keys));
    }

    @Override
    public String moduleLoad(String path) {
        return tryGetResource(jedis -> jedis.moduleLoad(path));
    }

    @Override
    public String moduleLoad(String path, String... args) {
        return tryGetResource(jedis -> jedis.moduleLoad(path, args));
    }

    @Override
    public String moduleUnload(String name) {
        return tryGetResource(jedis -> jedis.moduleUnload(name));
    }

    @Override
    public List<Module> moduleList() {
        return tryGetResource(Jedis::moduleList);
    }

    @Override
    public Boolean scriptExists(String sha1) {
        return tryGetResource(jedis -> jedis.scriptExists(sha1));
    }

    @Override
    public List<Boolean> scriptExists(String... sha1) {
        return tryGetResource(jedis -> jedis.scriptExists(sha1));
    }

    @Override
    public Boolean scriptExists(byte[] sha1) {
        return tryGetResource(jedis -> jedis.scriptExists(sha1));
    }

    @Override
    public List<Boolean> scriptExists(byte[]... sha1) {
        return tryGetResource(jedis -> jedis.scriptExists(sha1));
    }

    @Override
    public String scriptLoad(String script) {
        return tryGetResource(jedis -> jedis.scriptLoad(script));
    }

    @Override
    public byte[] scriptLoad(byte[] script) {
        return tryGetResource(jedis -> jedis.scriptLoad(script));
    }

    @Override
    public String scriptFlush() {
        return tryGetResource(Jedis::scriptFlush);
    }

    @Override
    public String scriptFlush(FlushMode flushMode) {
        return tryGetResource(jedis -> jedis.scriptFlush(flushMode));
    }

    @Override
    public String scriptKill() {
        return tryGetResource(Jedis::scriptKill);
    }

    @Override
    public Object eval(byte[] script) {
        return tryGetResource(jedis -> jedis.eval(script));
    }

    @Override
    public Object eval(byte[] script, int keyCount, byte[]... params) {
        return tryGetResource(jedis -> jedis.eval(script, keyCount, params));
    }

    @Override
    public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.eval(script, keys, args));
    }

    @Override
    public Object evalReadonly(byte[] script, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.evalReadonly(script, keys, args));
    }

    @Override
    public Object evalsha(byte[] sha1) {
        return tryGetResource(jedis -> jedis.evalsha(sha1));
    }

    @Override
    public Object evalsha(byte[] sha1, int keyCount, byte[]... params) {
        return tryGetResource(jedis -> jedis.evalsha(sha1, keyCount, params));
    }

    @Override
    public Object evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.evalsha(sha1, keys, args));
    }

    @Override
    public Object evalshaReadonly(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.evalshaReadonly(sha1, keys, args));
    }

    @Override
    public Object eval(String script) {
        return tryGetResource(jedis -> jedis.eval(script));
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        return tryGetResource(jedis -> jedis.eval(script, keyCount, params));
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.eval(script, keys, args));
    }

    @Override
    public Object evalReadonly(String script, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.evalReadonly(script, keys, args));
    }

    @Override
    public Object evalsha(String sha1) {
        return tryGetResource(jedis -> jedis.evalsha(sha1));
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return tryGetResource(jedis -> jedis.evalsha(sha1, keyCount, params));
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.evalsha(sha1, keys, args));
    }

    @Override
    public Object evalshaReadonly(String sha1, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.evalshaReadonly(sha1, keys, args));
    }

    @Override
    public String sentinelMyId() {
        return tryGetResource(Jedis::sentinelMyId);
    }

    @Override
    public List<Map<String, String>> sentinelMasters() {
        return tryGetResource(Jedis::sentinelMasters);
    }

    @Override
    public Map<String, String> sentinelMaster(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelMaster(masterName));
    }

    @Override
    public List<Map<String, String>> sentinelSentinels(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelSentinels(masterName));
    }

    @Override
    public List<String> sentinelGetMasterAddrByName(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelGetMasterAddrByName(masterName));
    }

    @Override
    public Long sentinelReset(String pattern) {
        return tryGetResource(jedis -> jedis.sentinelReset(pattern));
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<Map<String, String>> sentinelSlaves(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelSlaves(masterName));
    }

    @Override
    public List<Map<String, String>> sentinelReplicas(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelReplicas(masterName));
    }

    @Override
    public String sentinelFailover(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelFailover(masterName));
    }

    @Override
    public String sentinelMonitor(String masterName, String ip, int port, int quorum) {
        return tryGetResource(jedis -> jedis.sentinelMonitor(masterName, ip, port, quorum));
    }

    @Override
    public String sentinelRemove(String masterName) {
        return tryGetResource(jedis -> jedis.sentinelRemove(masterName));
    }

    @Override
    public String sentinelSet(String masterName, Map<String, String> parameterMap) {
        return tryGetResource(jedis -> jedis.sentinelSet(masterName, parameterMap));
    }

    @Override
    public String ping() {
        return tryGetResource(Jedis::ping);
    }

    @Override
    public String ping(String message) {
        return tryGetResource(jedis -> jedis.ping(message));
    }

    @Override
    public String echo(String string) {
        return tryGetResource(jedis -> jedis.echo(string));
    }

    @Override
    public byte[] echo(byte[] string) {
        return tryGetResource(jedis -> jedis.echo(string));
    }

    @Override
    public String quit() {
        return tryGetResource(Jedis::quit);
    }

    @Override
    public String flushDB() {
        return tryGetResource(Jedis::flushDB);
    }

    @Override
    public String flushAll() {
        return tryGetResource(Jedis::flushAll);
    }

    @Override
    public String flushAll(FlushMode flushMode) {
        return tryGetResource(jedis -> jedis.flushAll(flushMode));
    }

    @Override
    public String auth(String password) {
        return tryGetResource(jedis -> jedis.auth(password));
    }

    @Override
    public String auth(String user, String password) {
        return tryGetResource(jedis -> jedis.auth(user, password));
    }

    @Override
    public String save() {
        return tryGetResource(Jedis::save);
    }

    @Override
    public String bgsave() {
        return tryGetResource(Jedis::bgsave);
    }

    @Override
    public String bgsaveSchedule() {
        return tryGetResource(Jedis::bgsaveSchedule);
    }

    @Override
    public String bgrewriteaof() {
        return tryGetResource(Jedis::bgrewriteaof);
    }

    @Override
    public long lastsave() {
        return tryGetResource(Jedis::lastsave);
    }

    @Override
    public void shutdown() throws JedisException {
        tryGetResource(jedis -> {
            jedis.shutdown();
            return null;
        });
    }

    @Override
    public void shutdown(SaveMode saveMode) throws JedisException {
        tryGetResource(jedis -> {
            jedis.shutdown(saveMode);
            return null;
        });
    }

    @Override
    public void shutdown(ShutdownParams shutdownParams) throws JedisException {
        tryGetResource(jedis -> {
            jedis.shutdown(shutdownParams);
            return null;
        });
    }

    @Override
    public String shutdownAbort() {
        return tryGetResource(Jedis::shutdownAbort);
    }

    @Override
    public String info() {
        return tryGetResource(Jedis::info);
    }

    @Override
    public String info(String section) {
        return tryGetResource(jedis -> jedis.info(section));
    }

    @Override
    public String slaveof(String host, int port) {
        return tryGetResource(jedis -> jedis.slaveof(host, port));
    }

    @Override
    public String slaveofNoOne() {
        return tryGetResource(Jedis::slaveofNoOne);
    }

    @Override
    public String replicaof(String host, int port) {
        return tryGetResource(jedis -> jedis.replicaof(host, port));
    }

    @Override
    public String replicaofNoOne() {
        return tryGetResource(Jedis::replicaofNoOne);
    }

    @Override
    public long waitReplicas(int replicas, long timeout) {
        return tryGetResource(jedis -> jedis.waitReplicas(replicas, timeout));
    }

    @Override
    public String lolwut() {
        return tryGetResource(Jedis::lolwut);
    }

    @Override
    public String lolwut(LolwutParams lolwutParams) {
        return tryGetResource(jedis -> jedis.lolwut(lolwutParams));
    }

    @Override
    public long sadd(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.sadd(key, members));
    }

    @Override
    public Set<byte[]> smembers(byte[] key) {
        return tryGetResource(jedis -> jedis.smembers(key), true);
    }

    @Override
    public long srem(byte[] key, byte[]... member) {
        return tryGetResource(jedis -> jedis.srem(key, member));
    }

    @Override
    public byte[] spop(byte[] key) {
        return tryGetResource(jedis -> jedis.spop(key));
    }

    @Override
    public Set<byte[]> spop(byte[] key, long count) {
        return tryGetResource(jedis -> jedis.spop(key, count));
    }

    @Override
    public long scard(byte[] key) {
        return tryGetResource(jedis -> jedis.scard(key), true);
    }

    @Override
    public boolean sismember(byte[] key, byte[] member) {
        return tryGetResource(jedis -> jedis.sismember(key, member), true);
    }

    @Override
    public List<Boolean> smismember(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.smismember(key, members), true);
    }

    @Override
    public byte[] srandmember(byte[] key) {
        return tryGetResource(jedis -> jedis.srandmember(key));
    }

    @Override
    public List<byte[]> srandmember(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.srandmember(key, count));
    }

    @Override
    public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return tryGetResource(jedis -> jedis.sscan(key, cursor, params), true);
    }

    @Override
    public Set<byte[]> sdiff(byte[]... keys) {
        return tryGetResource(jedis -> jedis.sdiff(keys));
    }

    @Override
    public long sdiffstore(byte[] dstkey, byte[]... keys) {
        return tryGetResource(jedis -> jedis.sdiffstore(dstkey, keys));
    }

    @Override
    public Set<byte[]> sinter(byte[]... keys) {
        return tryGetResource(jedis -> jedis.sinter(keys));
    }

    @Override
    public long sinterstore(byte[] dstkey, byte[]... keys) {
        return tryGetResource(jedis -> jedis.sinterstore(dstkey, keys));
    }

    @Override
    public long sintercard(byte[]... keys) {
        return tryGetResource(jedis -> jedis.sintercard(keys));
    }

    @Override
    public long sintercard(int limit, byte[]... keys) {
        return tryGetResource(jedis -> jedis.sintercard(limit, keys));
    }

    @Override
    public Set<byte[]> sunion(byte[]... keys) {
        return tryGetResource(jedis -> jedis.sunion(keys));
    }

    @Override
    public long sunionstore(byte[] dstkey, byte[]... keys) {
        return tryGetResource(jedis -> jedis.sunionstore(dstkey, keys));
    }

    @Override
    public long smove(byte[] srckey, byte[] dstkey, byte[] member) {
        return tryGetResource(jedis -> jedis.smove(srckey, dstkey, member));
    }

    @Override
    public long sadd(String key, String... members) {
        return tryGetResource(jedis -> jedis.sadd(key, members));
    }

    @Override
    public Set<String> smembers(String key) {
        return tryGetResource(jedis -> jedis.smembers(key), true);
    }

    @Override
    public long srem(String key, String... members) {
        return tryGetResource(jedis -> jedis.srem(key, members));
    }

    @Override
    public String spop(String key) {
        return tryGetResource(jedis -> jedis.spop(key));
    }

    @Override
    public Set<String> spop(String key, long count) {
        return tryGetResource(jedis -> jedis.spop(key, count));
    }

    @Override
    public long scard(String key) {
        return tryGetResource(jedis -> jedis.scard(key), true);
    }

    @Override
    public boolean sismember(String key, String member) {
        return tryGetResource(jedis -> jedis.sismember(key, member), true);
    }

    @Override
    public List<Boolean> smismember(String key, String... members) {
        return tryGetResource(jedis -> jedis.smismember(key, members), true);
    }

    @Override
    public String srandmember(String key) {
        return tryGetResource(jedis -> jedis.srandmember(key));
    }

    @Override
    public List<String> srandmember(String key, int count) {
        return tryGetResource(jedis -> jedis.srandmember(key, count));
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.sscan(key, cursor, scanParams), true);
    }

    @Override
    public Set<String> sdiff(String... keys) {
        return tryGetResource(jedis -> jedis.sdiff(keys));
    }

    @Override
    public long sdiffstore(String dstkey, String... keys) {
        return tryGetResource(jedis -> jedis.sdiffstore(dstkey, keys));
    }

    @Override
    public Set<String> sinter(String... keys) {
        return tryGetResource(jedis -> jedis.sinter(keys));
    }

    @Override
    public long sinterstore(String dstkey, String... keys) {
        return tryGetResource(jedis -> jedis.sinterstore(dstkey, keys));
    }

    @Override
    public long sintercard(String... keys) {
        return tryGetResource(jedis -> jedis.sintercard(keys));
    }

    @Override
    public long sintercard(int limit, String... keys) {
        return tryGetResource(jedis -> jedis.sintercard(limit, keys));
    }

    @Override
    public Set<String> sunion(String... keys) {
        return tryGetResource(jedis -> jedis.sunion(keys));
    }

    @Override
    public long sunionstore(String dstkey, String... keys) {
        return tryGetResource(jedis -> jedis.sunionstore(dstkey, keys));
    }

    @Override
    public long smove(String srckey, String dstkey, String member) {
        return tryGetResource(jedis -> jedis.smove(srckey, dstkey, member));
    }

    @Override
    public String slowlogReset() {
        return tryGetResource(Jedis::slowlogReset);
    }

    @Override
    public long slowlogLen() {
        return tryGetResource(Jedis::slowlogLen);
    }

    @Override
    public List<Slowlog> slowlogGet() {
        return tryGetResource(Jedis::slowlogGet);
    }

    @Override
    public List<Object> slowlogGetBinary() {
        return tryGetResource(Jedis::slowlogGetBinary);
    }

    @Override
    public List<Slowlog> slowlogGet(long entries) {
        return tryGetResource(jedis -> jedis.slowlogGet(entries));
    }

    @Override
    public List<Object> slowlogGetBinary(long entries) {
        return tryGetResource(jedis -> jedis.slowlogGetBinary(entries));
    }

    @Override
    public long zadd(byte[] key, double score, byte[] member) {
        return tryGetResource(jedis -> jedis.zadd(key, score, member));
    }

    @Override
    public long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return tryGetResource(jedis -> jedis.zadd(key, score, member, params));
    }

    @Override
    public long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return tryGetResource(jedis -> jedis.zadd(key, scoreMembers));
    }

    @Override
    public long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams zAddParams) {
        return tryGetResource(jedis -> jedis.zadd(key, scoreMembers, zAddParams));
    }

    @Override
    public Double zaddIncr(byte[] key, double score, byte[] member, ZAddParams params) {
        return tryGetResource(jedis -> jedis.zaddIncr(key, score, member, params));
    }

    @Override
    public long zrem(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.zrem(key, members));
    }

    @Override
    public double zincrby(byte[] key, double increment, byte[] member) {
        return tryGetResource(jedis -> jedis.zincrby(key, increment, member));
    }

    @Override
    public Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params) {
        return tryGetResource(jedis -> jedis.zincrby(key, increment, member, params));
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        return tryGetResource(jedis -> jedis.zrank(key, member));
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        return tryGetResource(jedis -> jedis.zrevrank(key, member));
    }

    @Override
    public List<byte[]> zrange(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrange(key, start, stop), true);
    }

    @Override
    public List<byte[]> zrevrange(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrevrange(key, start, stop), true);
    }

    @Override
    public List<Tuple> zrangeWithScores(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrangeWithScores(key, start, stop), true);
    }

    @Override
    public List<Tuple> zrevrangeWithScores(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrevrangeWithScores(key, start, stop), true);
    }

    @Override
    public List<byte[]> zrange(byte[] key, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrange(key, zRangeParams), true);
    }

    @Override
    public List<Tuple> zrangeWithScores(byte[] key, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrangeWithScores(key, zRangeParams), true);
    }

    @Override
    public long zrangestore(byte[] dest, byte[] src, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrangestore(dest, src, zRangeParams));
    }

    @Override
    public byte[] zrandmember(byte[] key) {
        return tryGetResource(jedis -> jedis.zrandmember(key));
    }

    @Override
    public List<byte[]> zrandmember(byte[] key, long count) {
        return tryGetResource(jedis -> jedis.zrandmember(key, count));
    }

    @Override
    public List<Tuple> zrandmemberWithScores(byte[] key, long count) {
        return tryGetResource(jedis -> jedis.zrandmemberWithScores(key, count));
    }

    @Override
    public long zcard(byte[] key) {
        return tryGetResource(jedis -> jedis.zcard(key), true);
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        return tryGetResource(jedis -> jedis.zscore(key, member), true);
    }

    @Override
    public List<Double> zmscore(byte[] key, byte[]... members) {
        return tryGetResource(jedis -> jedis.zmscore(key, members), true);
    }

    @Override
    public Tuple zpopmax(byte[] key) {
        return tryGetResource(jedis -> jedis.zpopmax(key));
    }

    @Override
    public List<Tuple> zpopmax(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.zpopmax(key, count));
    }

    @Override
    public Tuple zpopmin(byte[] key) {
        return tryGetResource(jedis -> jedis.zpopmin(key));
    }

    @Override
    public List<Tuple> zpopmin(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.zpopmin(key, count));
    }

    @Override
    public long zcount(byte[] key, double min, double max) {
        return tryGetResource(jedis -> jedis.zcount(key, min, max), true);
    }

    @Override
    public long zcount(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zcount(key, min, max), true);
    }

    @Override
    public List<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max), true);
    }

    @Override
    public List<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max), true);
    }

    @Override
    public List<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min), true);
    }

    @Override
    public List<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max, offset, count), true);
    }

    @Override
    public List<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min), true);
    }

    @Override
    public List<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max, offset, count), true);
    }

    @Override
    public List<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count), true);
    }

    @Override
    public List<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min, offset, count), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min, offset, count), true);
    }

    @Override
    public long zremrangeByRank(byte[] key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zremrangeByRank(key, start, stop));
    }

    @Override
    public long zremrangeByScore(byte[] key, double min, double max) {
        return tryGetResource(jedis -> jedis.zremrangeByScore(key, min, max));
    }

    @Override
    public long zremrangeByScore(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zremrangeByScore(key, min, max));
    }

    @Override
    public long zlexcount(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zlexcount(key, min, max));
    }

    @Override
    public List<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zrangeByLex(key, min, max));
    }

    @Override
    public List<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByLex(key, min, max, offset, count));
    }

    @Override
    public List<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return tryGetResource(jedis -> jedis.zrevrangeByLex(key, max, min));
    }

    @Override
    public List<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByLex(key, max, min, offset, count));
    }

    @Override
    public long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return tryGetResource(jedis -> jedis.zremrangeByLex(key, min, max));
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.zscan(key, cursor, scanParams), true);
    }

    @Override
    public List<byte[]> bzpopmax(double timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.bzpopmax(timeout, keys));
    }

    @Override
    public List<byte[]> bzpopmin(double timeout, byte[]... keys) {
        return tryGetResource(jedis -> jedis.bzpopmin(timeout, keys));
    }

    @Override
    public Set<byte[]> zdiff(byte[]... keys) {
        return tryGetResource(jedis -> jedis.zdiff(keys));
    }

    @Override
    public Set<Tuple> zdiffWithScores(byte[]... keys) {
        return tryGetResource(jedis -> jedis.zdiffWithScores(keys));
    }

    @Override
    public long zdiffStore(byte[] dstkey, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zdiffStore(dstkey, keys));
    }

    @Override
    public Set<byte[]> zinter(ZParams zParams, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zinter(zParams, keys));
    }

    @Override
    public Set<Tuple> zinterWithScores(ZParams zParams, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zinterWithScores(zParams, keys));
    }

    @Override
    public long zinterstore(byte[] dstkey, byte[]... sets) {
        return tryGetResource(jedis -> jedis.zinterstore(dstkey, sets));
    }

    @Override
    public long zinterstore(byte[] dstkey, ZParams zParams, byte[]... sets) {
        return tryGetResource(jedis -> jedis.zinterstore(dstkey, zParams, sets));
    }

    @Override
    public long zintercard(byte[]... keys) {
        return tryGetResource(jedis -> jedis.zintercard(keys));
    }

    @Override
    public long zintercard(long limit, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zintercard(limit, keys));
    }

    @Override
    public Set<byte[]> zunion(ZParams zParams, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zunion(zParams, keys));
    }

    @Override
    public Set<Tuple> zunionWithScores(ZParams zParams, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zunionWithScores(zParams, keys));
    }

    @Override
    public long zunionstore(byte[] dstkey, byte[]... sets) {
        return tryGetResource(jedis -> jedis.zunionstore(dstkey, sets));
    }

    @Override
    public long zunionstore(byte[] dstkey, ZParams zParams, byte[]... sets) {
        return tryGetResource(jedis -> jedis.zunionstore(dstkey, zParams, sets));
    }

    @Override
    public KeyValue<byte[], List<Tuple>> zmpop(SortedSetOption option, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zmpop(option, keys));
    }

    @Override
    public KeyValue<byte[], List<Tuple>> zmpop(SortedSetOption option, int count, byte[]... keys) {
        return tryGetResource(jedis -> jedis.zmpop(option, count, keys));
    }

    @Override
    public KeyValue<byte[], List<Tuple>> bzmpop(long timeout, SortedSetOption option, byte[]... keys) {
        return tryGetResource(jedis -> jedis.bzmpop(timeout, option, keys));
    }

    @Override
    public KeyValue<byte[], List<Tuple>> bzmpop(long timeout, SortedSetOption option, int count, byte[]... keys) {
        return tryGetResource(jedis -> jedis.bzmpop(timeout, option, count, keys));
    }

    @Override
    public long zadd(String key, double score, String member) {
        return tryGetResource(jedis -> jedis.zadd(key, score, member));
    }

    @Override
    public long zadd(String key, double score, String member, ZAddParams zAddParams) {
        return tryGetResource(jedis -> jedis.zadd(key, score, member, zAddParams));
    }

    @Override
    public long zadd(String key, Map<String, Double> scoreMembers) {
        return tryGetResource(jedis -> jedis.zadd(key, scoreMembers));
    }

    @Override
    public long zadd(String key, Map<String, Double> scoreMembers, ZAddParams zAddParams) {
        return tryGetResource(jedis -> jedis.zadd(key, scoreMembers, zAddParams));
    }

    @Override
    public Double zaddIncr(String key, double score, String member, ZAddParams zAddParams) {
        return tryGetResource(jedis -> jedis.zaddIncr(key, score, member, zAddParams));
    }

    @Override
    public long zrem(String key, String... members) {
        return tryGetResource(jedis -> jedis.zrem(key, members));
    }

    @Override
    public double zincrby(String key, double increment, String member) {
        return tryGetResource(jedis -> jedis.zincrby(key, increment, member));
    }

    @Override
    public Double zincrby(String key,double increment, String member, ZIncrByParams zIncrByParams) {
        return tryGetResource(jedis -> jedis.zincrby(key, increment, member, zIncrByParams));
    }

    @Override
    public Long zrank(String key, String member) {
        return tryGetResource(jedis -> jedis.zrank(key, member));
    }

    @Override
    public Long zrevrank(String key, String member) {
        return tryGetResource(jedis -> jedis.zrevrank(key, member));
    }

    @Override
    public List<String> zrange(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrange(key, start, stop), true);
    }

    @Override
    public List<String> zrevrange(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrevrange(key, start, stop), true);
    }

    @Override
    public List<Tuple> zrangeWithScores(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrangeWithScores(key, start, stop), true);
    }

    @Override
    public List<Tuple> zrevrangeWithScores(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zrevrangeWithScores(key, start, stop), true);
    }

    @Override
    public List<String> zrange(String key, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrange(key, zRangeParams), true);
    }

    @Override
    public List<Tuple> zrangeWithScores(String key, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrangeWithScores(key, zRangeParams), true);
    }

    @Override
    public long zrangestore(String dest, String src, ZRangeParams zRangeParams) {
        return tryGetResource(jedis -> jedis.zrangestore(dest, src, zRangeParams));
    }

    @Override
    public String zrandmember(String key) {
        return tryGetResource(jedis -> jedis.zrandmember(key));
    }

    @Override
    public List<String> zrandmember(String key, long count) {
        return tryGetResource(jedis -> jedis.zrandmember(key, count));
    }

    @Override
    public List<Tuple> zrandmemberWithScores(String key, long count) {
        return tryGetResource(jedis -> jedis.zrandmemberWithScores(key, count));
    }

    @Override
    public long zcard(String key) {
        return tryGetResource(jedis -> jedis.zcard(key), true);
    }

    @Override
    public Double zscore(String key, String member) {
        return tryGetResource(jedis -> jedis.zscore(key, member), true);
    }

    @Override
    public List<Double> zmscore(String key, String... members) {
        return tryGetResource(jedis -> jedis.zmscore(key, members), true);
    }

    @Override
    public Tuple zpopmax(String key) {
        return tryGetResource(jedis -> jedis.zpopmax(key));
    }

    @Override
    public List<Tuple> zpopmax(String key, int count) {
        return tryGetResource(jedis -> jedis.zpopmax(key, count));
    }

    @Override
    public Tuple zpopmin(String key) {
        return tryGetResource(jedis -> jedis.zpopmin(key));
    }

    @Override
    public List<Tuple> zpopmin(String key, int count) {
        return tryGetResource(jedis -> jedis.zpopmin(key, count));
    }

    @Override
    public long zcount(String key, double min, double max) {
        return tryGetResource(jedis -> jedis.zcount(key, min, max), true);
    }

    @Override
    public long zcount(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zcount(key, min, max), true);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max), true);
    }

    @Override
    public List<String> zrangeByScore(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max), true);
    }

    @Override
    public List<String> zrevrangeByScore(String key, double max, double min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min), true);
    }

    @Override
    public List<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max, offset, count), true);
    }

    @Override
    public List<String> zrevrangeByScore(String key, String max, String min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min), true);
    }

    @Override
    public List<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScore(key, min, max, offset, count), true);
    }

    @Override
    public List<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count), true);
    }

    @Override
    public List<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScore(key, max, min, offset, count), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min), true);
    }

    @Override
    public List<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByScoreWithScores(key, min, max, offset, count), true);
    }

    @Override
    public List<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByScoreWithScores(key, max, min, offset, count), true);
    }

    @Override
    public long zremrangeByRank(String key, long start, long stop) {
        return tryGetResource(jedis -> jedis.zremrangeByRank(key, start, stop));
    }

    @Override
    public long zremrangeByScore(String key, double min, double max) {
        return tryGetResource(jedis -> jedis.zremrangeByScore(key, min, max));
    }

    @Override
    public long zremrangeByScore(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zremrangeByScore(key, min, max));
    }

    @Override
    public long zlexcount(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zlexcount(key, min, max));
    }

    @Override
    public List<String> zrangeByLex(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zrangeByLex(key, min, max));
    }

    @Override
    public List<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrangeByLex(key, min, max, offset, count));
    }

    @Override
    public List<String> zrevrangeByLex(String key, String max, String min) {
        return tryGetResource(jedis -> jedis.zrevrangeByLex(key, max, min));
    }

    @Override
    public List<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return tryGetResource(jedis -> jedis.zrevrangeByLex(key, max, min, offset, count));
    }

    @Override
    public long zremrangeByLex(String key, String min, String max) {
        return tryGetResource(jedis -> jedis.zremrangeByLex(key, min, max));
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams scanParams) {
        return tryGetResource(jedis -> jedis.zscan(key, cursor, scanParams), true);
    }

    @Override
    public KeyedZSetElement bzpopmax(double timeout, String... keys) {
        return tryGetResource(jedis -> jedis.bzpopmax(timeout, keys));
    }

    @Override
    public KeyedZSetElement bzpopmin(double timeout, String... keys) {
        return tryGetResource(jedis -> jedis.bzpopmin(timeout, keys));
    }

    @Override
    public Set<String> zdiff(String... keys) {
        return tryGetResource(jedis -> jedis.zdiff(keys));
    }

    @Override
    public Set<Tuple> zdiffWithScores(String... keys) {
        return tryGetResource(jedis -> jedis.zdiffWithScores(keys));
    }

    @Override
    public long zdiffStore(String dstkey, String... keys) {
        return tryGetResource(jedis -> jedis.zdiffStore(dstkey, keys));
    }

    @Override
    public long zinterstore(String dstkey, String... keys) {
        return tryGetResource(jedis -> jedis.zinterstore(dstkey, keys));
    }

    @Override
    public long zinterstore(String dstkey, ZParams zParams, String... sets) {
        return tryGetResource(jedis -> jedis.zinterstore(dstkey, zParams, sets));
    }

    @Override
    public long zintercard(String... keys) {
        return tryGetResource(jedis -> jedis.zintercard(keys));
    }

    @Override
    public long zintercard(long limit, String... keys) {
        return tryGetResource(jedis -> jedis.zintercard(limit, keys));
    }

    @Override
    public Set<String> zinter(ZParams zParams, String... keys) {
        return tryGetResource(jedis -> jedis.zinter(zParams, keys));
    }

    @Override
    public Set<Tuple> zinterWithScores(ZParams zParams, String... keys) {
        return tryGetResource(jedis -> jedis.zinterWithScores(zParams, keys));
    }

    @Override
    public Set<String> zunion(ZParams zParams, String... keys) {
        return tryGetResource(jedis -> jedis.zunion(zParams, keys));
    }

    @Override
    public Set<Tuple> zunionWithScores(ZParams zParams, String... keys) {
        return tryGetResource(jedis -> jedis.zunionWithScores(zParams, keys));
    }

    @Override
    public long zunionstore(String dstkey, String... sets) {
        return tryGetResource(jedis -> jedis.zunionstore(dstkey, sets));
    }

    @Override
    public long zunionstore(String dstkey, ZParams zParams, String... sets) {
        return tryGetResource(jedis -> jedis.zunionstore(dstkey, zParams, sets));
    }

    @Override
    public KeyValue<String, List<Tuple>> zmpop(SortedSetOption option, String... keys) {
        return tryGetResource(jedis -> jedis.zmpop(option, keys));
    }

    @Override
    public KeyValue<String, List<Tuple>> zmpop(SortedSetOption option, int count, String... keys) {
        return tryGetResource(jedis -> jedis.zmpop(option, count, keys));
    }

    @Override
    public KeyValue<String, List<Tuple>> bzmpop(long timeout, SortedSetOption option, String... keys) {
        return tryGetResource(jedis -> jedis.bzmpop(timeout, option, keys));
    }

    @Override
    public KeyValue<String, List<Tuple>> bzmpop(long timeout, SortedSetOption option, int count, String... keys) {
        return tryGetResource(jedis -> jedis.bzmpop(timeout, option, count, keys));
    }

    @Override
    public byte[] xadd(byte[] key, XAddParams xAddParams, Map<byte[], byte[]> hash) {
        return tryGetResource(jedis -> jedis.xadd(key, xAddParams, hash));
    }

    @Override
    public long xlen(byte[] key) {
        return tryGetResource(jedis -> jedis.xlen(key));
    }

    @Override
    public List<byte[]> xrange(byte[] key, byte[] start, byte[] end) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end));
    }

    @Override
    public List<byte[]> xrange(byte[] key, byte[] start, byte[] end, int count) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end, count));
    }

    @Override
    public List<byte[]> xrevrange(byte[] key, byte[] end, byte[] start) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start));
    }

    @Override
    public List<byte[]> xrevrange(byte[] key, byte[] end, byte[] start, int count) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start, count));
    }

    @Override
    public long xack(byte[] key, byte[] group, byte[]... ids) {
        return tryGetResource(jedis -> jedis.xack(key, group, ids));
    }

    @Override
    public String xgroupCreate(byte[] key, byte[] consumer, byte[] id, boolean makeStream) {
        return tryGetResource(jedis -> jedis.xgroupCreate(key, consumer, id, makeStream));
    }

    @Override
    public String xgroupSetID(byte[] key, byte[] consumer, byte[] id) {
        return tryGetResource(jedis -> jedis.xgroupSetID(key, consumer, id));
    }

    @Override
    public long xgroupDestroy(byte[] key, byte[] consumer) {
        return tryGetResource(jedis -> jedis.xgroupDestroy(key, consumer));
    }

    @Override
    public boolean xgroupCreateConsumer(byte[] key, byte[] groupName, byte[] consumerName) {
        return tryGetResource(jedis -> jedis.xgroupCreateConsumer(key, groupName, consumerName));
    }

    @Override
    public long xgroupDelConsumer(byte[] key, byte[] consumer, byte[] consumerName) {
        return tryGetResource(jedis -> jedis.xgroupDelConsumer(key, consumer, consumerName));
    }

    @Override
    public long xdel(byte[] key, byte[]... ids) {
        return tryGetResource(jedis -> jedis.xdel(key, ids));
    }

    @Override
    public long xtrim(byte[] key, long maxLen, boolean approximateLength) {
        return tryGetResource(jedis -> jedis.xtrim(key, maxLen, approximateLength));
    }

    @Override
    public long xtrim(byte[] key, XTrimParams xTrimParams) {
        return tryGetResource(jedis -> jedis.xtrim(key, xTrimParams));
    }

    @Override
    public Object xpending(byte[] key, byte[] groupname) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname));
    }

    @Override
    public List<Object> xpending(byte[] key, byte[] groupname, byte[] start, byte[] end, int count, byte[] consumername) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname, start, end, count, consumername));
    }

    @Override
    public List<Object> xpending(byte[] key, byte[] groupname, XPendingParams params) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname, params));
    }

    @Override
    public List<byte[]> xclaim(byte[] key, byte[] group, byte[] consumername, long minIdleTime, XClaimParams params, byte[]... ids) {
        return tryGetResource(jedis -> jedis.xclaim(key, group, consumername, minIdleTime, params, ids));
    }

    @Override
    public List<byte[]> xclaimJustId(byte[] key, byte[] group, byte[] consumername, long minIdleTime, XClaimParams params, byte[]... ids) {
        return tryGetResource(jedis -> jedis.xclaimJustId(key, group, consumername, minIdleTime, params, ids));
    }

    @Override
    public List<Object> xautoclaim(byte[] key, byte[] groupName, byte[] consumerName, long minIdleTime, byte[] start, XAutoClaimParams params) {
        return tryGetResource(jedis -> jedis.xautoclaim(key, groupName, consumerName, minIdleTime, start, params));
    }

    @Override
    public List<Object> xautoclaimJustId(byte[] key, byte[] groupName, byte[] consumerName, long minIdleTime, byte[] start, XAutoClaimParams params) {
        return tryGetResource(jedis -> jedis.xautoclaimJustId(key, groupName, consumerName, minIdleTime, start, params));
    }

    @Override
    public Object xinfoStream(byte[] key) {
        return tryGetResource(jedis -> jedis.xinfoStream(key));
    }

    @Override
    public Object xinfoStreamFull(byte[] key) {
        return tryGetResource(jedis -> jedis.xinfoStreamFull(key));
    }

    @Override
    public Object xinfoStreamFull(byte[] key, int count) {
        return tryGetResource(jedis -> jedis.xinfoStreamFull(key, count));
    }

    @Override
    public List<Object> xinfoGroup(byte[] key) {
        return tryGetResource(jedis -> jedis.xinfoGroup(key));
    }

    @Override
    public List<Object> xinfoGroups(byte[] key) {
        return tryGetResource(jedis -> jedis.xinfoGroups(key));
    }

    @Override
    public List<Object> xinfoConsumers(byte[] key, byte[] group) {
        return tryGetResource(jedis -> jedis.xinfoConsumers(key, group));
    }

    @Override
    @SafeVarargs
    public final List<byte[]> xread(XReadParams xReadParams, Map.Entry<byte[], byte[]>... streams) {
        return tryGetResource(jedis -> jedis.xread(xReadParams, streams));
    }

    @Override
    @SafeVarargs
    public final List<byte[]> xreadGroup(byte[] groupname, byte[] consumer, XReadGroupParams xReadGroupParams, Map.Entry<byte[], byte[]>... streams) {
        return tryGetResource(jedis -> jedis.xreadGroup(groupname, consumer, xReadGroupParams, streams));
    }

    @Override
    public StreamEntryID xadd(String key, StreamEntryID id, Map<String, String> hash) {
        return tryGetResource(jedis -> jedis.xadd(key, id, hash));
    }

    @Override
    public StreamEntryID xadd(String key, XAddParams xAddParams, Map<String, String> hash) {
        return tryGetResource(jedis -> jedis.xadd(key, xAddParams, hash));
    }

    @Override
    public long xlen(String key) {
        return tryGetResource(jedis -> jedis.xlen(key));
    }

    @Override
    public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end));
    }

    @Override
    public List<StreamEntry> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end, count));
    }

    @Override
    public List<StreamEntry> xrevrange(String key, StreamEntryID end, StreamEntryID start) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start));
    }

    @Override
    public List<StreamEntry> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start, count));
    }

    @Override
    public List<StreamEntry> xrange(String key, String start, String end) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end));
    }

    @Override
    public List<StreamEntry> xrange(String key, String start, String end, int count) {
        return tryGetResource(jedis -> jedis.xrange(key, start, end, count));
    }

    @Override
    public List<StreamEntry> xrevrange(String key, String end, String start) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start));
    }

    @Override
    public List<StreamEntry> xrevrange(String key, String end, String start, int count) {
        return tryGetResource(jedis -> jedis.xrevrange(key, end, start, count));
    }

    @Override
    public long xack(String key, String group, StreamEntryID... ids) {
        return tryGetResource(jedis -> jedis.xack(key, group, ids));
    }

    @Override
    public String xgroupCreate(String key, String groupname, StreamEntryID id, boolean makeStream) {
        return tryGetResource(jedis -> jedis.xgroupCreate(key, groupname, id, makeStream));
    }

    @Override
    public String xgroupSetID(String key, String groupname, StreamEntryID id) {
        return tryGetResource(jedis -> jedis.xgroupSetID(key, groupname, id));
    }

    @Override
    public long xgroupDestroy(String key, String groupname) {
        return tryGetResource(jedis -> jedis.xgroupDestroy(key, groupname));
    }

    @Override
    public boolean xgroupCreateConsumer(String key, String groupName, String consumerName) {
        return tryGetResource(jedis -> jedis.xgroupCreateConsumer(key, groupName, consumerName));
    }

    @Override
    public long xgroupDelConsumer(String key, String groupname, String consumerName) {
        return tryGetResource(jedis -> jedis.xgroupDelConsumer(key, groupname, consumerName));
    }

    @Override
    public StreamPendingSummary xpending(String key, String groupname) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname));
    }

    @Override
    public List<StreamPendingEntry> xpending(String key, String groupname, StreamEntryID start, StreamEntryID end, int count, String consumername) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname, start, end, count, consumername));
    }

    @Override
    public List<StreamPendingEntry> xpending(String key, String groupname, XPendingParams xPendingParams) {
        return tryGetResource(jedis -> jedis.xpending(key, groupname, xPendingParams));
    }

    @Override
    public long xdel(String key, StreamEntryID... ids) {
        return tryGetResource(jedis -> jedis.xdel(key, ids));
    }

    @Override
    public long xtrim(String key, long maxLen, boolean approximateLength) {
        return tryGetResource(jedis -> jedis.xtrim(key, maxLen, approximateLength));
    }

    @Override
    public long xtrim(String key, XTrimParams xTrimParams) {
        return tryGetResource(jedis -> jedis.xtrim(key, xTrimParams));
    }

    @Override
    public List<StreamEntry> xclaim(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
        return tryGetResource(jedis -> jedis.xclaim(key, group, consumername, minIdleTime, params, ids));
    }

    @Override
    public List<StreamEntryID> xclaimJustId(String key, String group, String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
        return tryGetResource(jedis -> jedis.xclaimJustId(key, group, consumername, minIdleTime, params, ids));
    }

    @Override
    public Map.Entry<StreamEntryID, List<StreamEntry>> xautoclaim(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        return tryGetResource(jedis -> jedis.xautoclaim(key, group, consumerName, minIdleTime, start, params));
    }

    @Override
    public Map.Entry<StreamEntryID, List<StreamEntryID>> xautoclaimJustId(String key, String group, String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        return tryGetResource(jedis -> jedis.xautoclaimJustId(key, group, consumerName, minIdleTime, start, params));
    }

    @Override
    public StreamInfo xinfoStream(String key) {
        return tryGetResource(jedis -> jedis.xinfoStream(key));
    }

    @Override
    public StreamFullInfo xinfoStreamFull(String key) {
        return tryGetResource(jedis -> jedis.xinfoStreamFull(key));
    }

    @Override
    public StreamFullInfo xinfoStreamFull(String key, int count) {
        return tryGetResource(jedis -> jedis.xinfoStreamFull(key, count));
    }

    @Override
    public List<StreamGroupInfo> xinfoGroup(String key) {
        return tryGetResource(jedis -> jedis.xinfoGroup(key));
    }

    @Override
    public List<StreamGroupInfo> xinfoGroups(String key) {
        return tryGetResource(jedis -> jedis.xinfoGroups(key));
    }

    @Override
    public List<StreamConsumersInfo> xinfoConsumers(String key, String group) {
        return tryGetResource(jedis -> jedis.xinfoConsumers(key, group));
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xread(XReadParams xReadParams, Map<String, StreamEntryID> streams) {
        return tryGetResource(jedis -> jedis.xread(xReadParams, streams));
    }

    @Override
    public List<Map.Entry<String, List<StreamEntry>>> xreadGroup(String groupname, String consumer, XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams) {
        return tryGetResource(jedis -> jedis.xreadGroup(groupname, consumer, xReadGroupParams, streams));
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return tryGetResource(jedis -> jedis.set(key, value));
    }

    @Override
    public String set(byte[] key, byte[] value, SetParams params) {
        return tryGetResource(jedis -> jedis.set(key, value, params));
    }

    @Override
    public byte[] get(byte[] key) {
        return tryGetResource(jedis -> jedis.get(key), true);
    }

    @Override
    public byte[] getDel(byte[] key) {
        return tryGetResource(jedis -> jedis.getDel(key));
    }

    @Override
    public byte[] getEx(byte[] key, GetExParams params) {
        return tryGetResource(jedis -> jedis.getEx(key, params));
    }

    @Override
    public boolean setbit(byte[] key, long offset, boolean value) {
        return tryGetResource(jedis -> jedis.setbit(key, offset, value));
    }

    @Override
    public boolean getbit(byte[] key, long offset) {
        return tryGetResource(jedis -> jedis.getbit(key, offset));
    }

    @Override
    public long setrange(byte[] key, long offset, byte[] value) {
        return tryGetResource(jedis -> jedis.setrange(key, offset, value));
    }

    @Override
    public byte[] getrange(byte[] key, long startOffset, long endOffset) {
        return tryGetResource(jedis -> jedis.getrange(key, startOffset, endOffset));
    }

    @Override
    public byte[] getSet(byte[] key, byte[] value) {
        return tryGetResource(jedis -> jedis.getSet(key, value));
    }

    @Override
    public long setnx(byte[] key, byte[] value) {
        return tryGetResource(jedis -> jedis.setnx(key, value));
    }

    @Override
    public String setex(byte[] key, long seconds, byte[] value) {
        return tryGetResource(jedis -> jedis.setex(key, seconds, value));
    }

    @Override
    public String psetex(byte[] key, long milliseconds, byte[] value) {
        return tryGetResource(jedis -> jedis.psetex(key, milliseconds, value));
    }

    @Override
    public List<byte[]> mget(byte[]... keys) {
        return tryGetResource(jedis -> jedis.mget(keys), true);
    }

    @Override
    public String mset(byte[]... keysvalues) {
        return tryGetResource(jedis -> jedis.mset(keysvalues));
    }

    @Override
    public long msetnx(byte[]... keysvalues) {
        return tryGetResource(jedis -> jedis.msetnx(keysvalues));
    }

    @Override
    public long incr(byte[] key) {
        return tryGetResource(jedis -> jedis.incr(key));
    }

    @Override
    public long incrBy(byte[] key, long increment) {
        return tryGetResource(jedis -> jedis.incrBy(key, increment));
    }

    @Override
    public double incrByFloat(byte[] key, double increment) {
        return tryGetResource(jedis -> jedis.incrByFloat(key, increment));
    }

    @Override
    public long decr(byte[] key) {
        return tryGetResource(jedis -> jedis.decr(key));
    }

    @Override
    public long decrBy(byte[] key, long decrement) {
        return tryGetResource(jedis -> jedis.decrBy(key, decrement));
    }

    @Override
    public long append(byte[] key, byte[] value) {
        return tryGetResource(jedis -> jedis.append(key, value));
    }

    @Override
    public byte[] substr(byte[] key, int start, int end) {
        return tryGetResource(jedis -> jedis.substr(key, start, end));
    }

    @Override
    public long strlen(byte[] key) {
        return tryGetResource(jedis -> jedis.strlen(key), true);
    }

    @Override
    public long bitcount(byte[] key) {
        return tryGetResource(jedis -> jedis.bitcount(key));
    }

    @Override
    public long bitcount(byte[] key, long start, long end) {
        return tryGetResource(jedis -> jedis.bitcount(key, start, end));
    }

    @Override
    public long bitcount(byte[] key, long start, long end, BitCountOption option) {
        return tryGetResource(jedis -> jedis.bitcount(key, start, end, option));
    }

    @Override
    public long bitpos(byte[] key, boolean value) {
        return tryGetResource(jedis -> jedis.bitpos(key, value));
    }

    @Override
    public long bitpos(byte[] key, boolean value, BitPosParams params) {
        return tryGetResource(jedis -> jedis.bitpos(key, value, params));
    }

    @Override
    public List<Long> bitfield(byte[] key, byte[]... arguments) {
        return tryGetResource(jedis -> jedis.bitfield(key, arguments));
    }

    @Override
    public List<Long> bitfieldReadonly(byte[] key, byte[]... arguments) {
        return tryGetResource(jedis -> jedis.bitfieldReadonly(key, arguments));
    }

    @Override
    public long bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
        return tryGetResource(jedis -> jedis.bitop(op, destKey, srcKeys));
    }

    @Override
    public LCSMatchResult strAlgoLCSKeys(byte[] keyA, byte[] keyB, StrAlgoLCSParams params) {
        return tryGetResource(jedis -> jedis.strAlgoLCSKeys(keyA, keyB, params));
    }

    @Override
    public LCSMatchResult lcs(byte[] keyA, byte[] keyB, LCSParams params) {
        return tryGetResource(jedis -> jedis.lcs(keyA, keyB, params));
    }

    @Override
    public String set(String key, String value) {
        return tryGetResource(jedis -> jedis.set(key, value));
    }

    @Override
    public String set(String key, String value, SetParams params) {
        return tryGetResource(jedis -> jedis.set(key, value, params));
    }

    @Override
    public String get(String key) {
        return tryGetResource(jedis -> jedis.get(key), true);
    }

    @Override
    public String getDel(String key) {
        return tryGetResource(jedis -> jedis.getDel(key));
    }

    @Override
    public String getEx(String key, GetExParams params) {
        return tryGetResource(jedis -> jedis.getEx(key, params));
    }

    @Override
    public boolean setbit(String key, long offset, boolean value) {
        return tryGetResource(jedis -> jedis.setbit(key, offset, value));
    }

    @Override
    public boolean getbit(String key, long offset) {
        return tryGetResource(jedis -> jedis.getbit(key, offset));
    }

    @Override
    public long setrange(String key, long offset, String value) {
        return tryGetResource(jedis -> jedis.setrange(key, offset, value));
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        return tryGetResource(jedis -> jedis.getrange(key, startOffset, endOffset));
    }

    @Override
    public String getSet(String key, String value) {
        return tryGetResource(jedis -> jedis.getSet(key, value));
    }

    @Override
    public long setnx(String key, String value) {
        return tryGetResource(jedis -> jedis.setnx(key, value));
    }

    @Override
    public String setex(String key, long seconds, String value) {
        return tryGetResource(jedis -> jedis.setex(key, seconds, value));
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        return tryGetResource(jedis -> jedis.psetex(key, milliseconds, value));
    }

    @Override
    public List<String> mget(String... keys) {
        return tryGetResource(jedis -> jedis.mget(keys), true);
    }

    @Override
    public String mset(String... keysvalues) {
        return tryGetResource(jedis -> jedis.mset(keysvalues));
    }

    @Override
    public long msetnx(String... keysvalues) {
        return tryGetResource(jedis -> jedis.msetnx(keysvalues));
    }

    @Override
    public long incr(String key) {
        return tryGetResource(jedis -> jedis.incr(key));
    }

    @Override
    public long incrBy(String key, long increment) {
        return tryGetResource(jedis -> jedis.incrBy(key, increment));
    }

    @Override
    public double incrByFloat(String key, double increment) {
        return tryGetResource(jedis -> jedis.incrByFloat(key, increment));
    }

    @Override
    public long decr(String key) {
        return tryGetResource(jedis -> jedis.decr(key));
    }

    @Override
    public long decrBy(String key, long decrement) {
        return tryGetResource(jedis -> jedis.decrBy(key, decrement));
    }

    @Override
    public long append(String key, String value) {
        return tryGetResource(jedis -> jedis.append(key, value));
    }

    @Override
    public String substr(String key, int start, int end) {
        return tryGetResource(jedis -> jedis.substr(key, start, end));
    }

    @Override
    public long strlen(String key) {
        return tryGetResource(jedis -> jedis.strlen(key), true);
    }

    @Override
    public long bitcount(String key) {
        return tryGetResource(jedis -> jedis.bitcount(key));
    }

    @Override
    public long bitcount(String key, long start, long end) {
        return tryGetResource(jedis -> jedis.bitcount(key, start, end));
    }

    @Override
    public long bitcount(String key, long start, long end, BitCountOption option) {
        return tryGetResource(jedis -> jedis.bitcount(key, start, end, option));
    }

    @Override
    public long bitpos(String key, boolean value) {
        return tryGetResource(jedis -> jedis.bitpos(key, value));
    }

    @Override
    public long bitpos(String key, boolean value, BitPosParams params) {
        return tryGetResource(jedis -> jedis.bitpos(key, value, params));
    }

    @Override
    public List<Long> bitfield(String key, String... arguments) {
        return tryGetResource(jedis -> jedis.bitfield(key, arguments));
    }

    @Override
    public List<Long> bitfieldReadonly(String key, String... arguments) {
        return tryGetResource(jedis -> jedis.bitfieldReadonly(key, arguments));
    }

    @Override
    public long bitop(BitOP op, String destKey, String... srcKeys) {
        return tryGetResource(jedis -> jedis.bitop(op, destKey, srcKeys));
    }

    @Override
    public LCSMatchResult strAlgoLCSKeys(String keyA, String keyB, StrAlgoLCSParams params) {
        return tryGetResource(jedis -> jedis.strAlgoLCSKeys(keyA, keyB, params));
    }

    @Override
    public LCSMatchResult lcs(String keyA, String keyB, LCSParams params) {
        return tryGetResource(jedis -> jedis.lcs(keyA, keyB, params));
    }

    @Override
    public List<Object> multi(JedisMultiCallback callback) {
        return tryGetResource(jedis -> {
            Transaction transaction = jedis.multi();
            callback.apply(transaction);
            return transaction.exec();
        });
    }

    @Override
    public List<Object> doInMasterPipeline(JedisPipelineCallback callback) {
        return tryGetResource(jedis -> {
            Pipeline pipeline = jedis.pipelined();
            callback.apply(pipeline);
            return pipeline.syncAndReturnAll();
        });
    }

    @Override
    public List<Object> doInSlavePipeline(JedisPipelineCallback callback) {
        return tryGetResource(jedis -> {
            Pipeline pipeline = jedis.pipelined();
            callback.apply(pipeline);
            return pipeline.syncAndReturnAll();
        }, true);
    }

    @Override
    public Object fcall(byte[] name, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.fcall(name, keys, args));
    }

    @Override
    public Object fcallReadonly(byte[] name, List<byte[]> keys, List<byte[]> args) {
        return tryGetResource(jedis -> jedis.fcallReadonly(name, keys, args));
    }

    @Override
    public String functionDelete(byte[] libraryName) {
        return tryGetResource(jedis -> jedis.functionDelete(libraryName));
    }

    @Override
    public List<Object> functionListBinary() {
        return tryGetResource(Jedis::functionListBinary);
    }

    @Override
    public List<Object> functionList(byte[] libraryNamePattern) {
        return tryGetResource(jedis -> jedis.functionList(libraryNamePattern));
    }

    @Override
    public List<Object> functionListWithCodeBinary() {
        return tryGetResource(Jedis::functionListWithCodeBinary);
    }

    @Override
    public List<Object> functionListWithCode(byte[] libraryNamePattern) {
        return tryGetResource(jedis -> jedis.functionListWithCode(libraryNamePattern));
    }

    @Override
    public String functionLoad(byte[] functionCode) {
        return tryGetResource(jedis -> jedis.functionLoad(functionCode));
    }

    @Override
    public String functionLoadReplace(byte[] functionCode) {
        return tryGetResource(jedis -> jedis.functionLoadReplace(functionCode));
    }

    @Override
    public Object functionStatsBinary() {
        return tryGetResource(Jedis::functionStatsBinary);
    }

    @Override
    public Object fcall(String name, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.fcall(name, keys, args));
    }

    @Override
    public Object fcallReadonly(String name, List<String> keys, List<String> args) {
        return tryGetResource(jedis -> jedis.fcallReadonly(name, keys, args));
    }

    @Override
    public String functionDelete(String libraryName) {
        return tryGetResource(jedis -> jedis.functionDelete(libraryName));
    }

    @Override
    public byte[] functionDump() {
        return tryGetResource(Jedis::functionDump);
    }

    @Override
    public String functionFlush() {
        return tryGetResource(Jedis::functionFlush);
    }

    @Override
    public String functionFlush(FlushMode mode) {
        return tryGetResource(jedis -> jedis.functionFlush(mode));
    }

    @Override
    public String functionKill() {
        return tryGetResource(Jedis::functionKill);
    }

    @Override
    public List<LibraryInfo> functionList() {
        return tryGetResource(Jedis::functionList);
    }

    @Override
    public List<LibraryInfo> functionList(String libraryNamePattern) {
        return tryGetResource(jedis -> jedis.functionList(libraryNamePattern));
    }

    @Override
    public List<LibraryInfo> functionListWithCode() {
        return tryGetResource(Jedis::functionListWithCode);
    }

    @Override
    public List<LibraryInfo> functionListWithCode(String libraryNamePattern) {
        return tryGetResource(jedis -> jedis.functionListWithCode(libraryNamePattern));
    }

    @Override
    public String functionLoad(String functionCode) {
        return tryGetResource(jedis -> jedis.functionLoad(functionCode));
    }

    @Override
    public String functionLoadReplace(String functionCode) {
        return tryGetResource(jedis -> jedis.functionLoadReplace(functionCode));
    }

    @Override
    public String functionRestore(byte[] serializedValue) {
        return tryGetResource(jedis -> jedis.functionRestore(serializedValue));
    }

    @Override
    public String functionRestore(byte[] serializedValue, FunctionRestorePolicy policy) {
        return tryGetResource(jedis -> jedis.functionRestore(serializedValue, policy));
    }

    @Override
    public FunctionStats functionStats() {
        return tryGetResource(Jedis::functionStats);
    }
}

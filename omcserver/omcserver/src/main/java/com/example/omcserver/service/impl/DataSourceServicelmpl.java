package com.example.omcserver.service.impl;

import com.example.omcserver.model.Alarm;
import com.example.omcserver.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataSourceServicelmpl implements DataSourceService {
    @Autowired
    private JdbcTemplate jdbcTemplateOne;
    @Autowired
    private JdbcTemplate jdbcTemplateTwo;

    @Override
    public int getMaxId() {
        try {
            String sql = "select max(alarmSeq) from omc_alert_information_tb";
            Integer i = jdbcTemplateOne.queryForObject(sql, Integer.class);
            return i == null ? 1 : i;
        } catch (Exception e) {
            log.error("异常",e);
            throw e;
        }
    }

    @Override
    public void addAlarm(Alarm alarm, String bussiness) {
        try {
            String sql = "INSERT INTO omc_alert_information_tb (alarmTitle,alarmStatus,alarmType,origSeverity,eventTime,alarmId,alarmSeq,specificProblemID,specificProblem,neUID,neName,neType,objectUID,objectName,objectType,locationInfo,addInfo,status,alarmed,bussiness)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            //2.调用 update语句，执行方法
            jdbcTemplateOne.update(sql, alarm.getAlarmTitle(), alarm.getAlarmStatus(), alarm.getAlarmType(), alarm.getOrigSeverity(), alarm.getEventTime(), alarm.getAlarmId(), alarm.getAlarmSeq(), alarm.getSpecificProblemID(), alarm.getSpecificProblem(), alarm.getNeUID(), alarm.getNeName(), alarm.getNeType(), alarm.getObjectUID(), alarm.getObjectName(), alarm.getObjectType(), alarm.getLocationInfo(), alarm.getAddInfo(), false, false, bussiness);
        } catch (Exception e) {
            log.error("异常",e);
            throw e;
        }
    }

    @Override
    public List<Alarm> listAlarm() {
        String sql = "select * from omc_alert_information_tb where status=false";
        List<Alarm> alarms = jdbcTemplateOne.query(sql, new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getInt("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getInt("alarmSeq"));
                alarm.setSpecificProblemID(rs.getString("specificProblemID"));
                alarm.setSpecificProblem(rs.getString("specificProblem"));
                alarm.setNeUID(rs.getString("neUID"));
                alarm.setNeName(rs.getString("neName"));
                alarm.setNeType(rs.getString("neType"));
                alarm.setObjectUID(rs.getString("objectUID"));
                alarm.setObjectName(rs.getString("objectName"));
                alarm.setObjectType(rs.getString("objectType"));
                alarm.setLocationInfo(rs.getString("locationInfo"));
                alarm.setAddInfo(rs.getString("addInfo"));
                return alarm;
            }
        });
        log.info("alarms:" + alarms);
        return alarms;
    }

    @Override
    public List<Alarm> listAlarm(int alarmSeq) {
        String sql = "select * from omc_alert_information_tb where alarmSeq>=? and status=true and flowTime is not null";

        List<Alarm> alarms = jdbcTemplateOne.query(sql, new Object[]{alarmSeq}, new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getInt("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getInt("alarmSeq"));
                alarm.setSpecificProblemID(rs.getString("specificProblemID"));
                alarm.setSpecificProblem(rs.getString("specificProblem"));
                alarm.setNeUID(rs.getString("neUID"));
                alarm.setNeName(rs.getString("neName"));
                alarm.setNeType(rs.getString("neType"));
                alarm.setObjectUID(rs.getString("objectUID"));
                alarm.setObjectName(rs.getString("objectName"));
                alarm.setObjectType(rs.getString("objectType"));
                alarm.setLocationInfo(rs.getString("locationInfo"));
                alarm.setAddInfo(rs.getString("addInfo"));
                return alarm;
            }
        });
        return alarms;
    }

    @Override
    public List<Alarm> listAlarm(String startTime, String endTime,String syncSource) {
        String sql = "";
        if ("".equals(startTime)||"null".equals(startTime)) {
            startTime = null;
        }
        if ("".equals(endTime)||"null".equals(endTime)) {
            endTime = null;
        }
        if(syncSource.equals("0")) {
            if (startTime != null && endTime == null) {
                sql = "select * from omc_alert_information_tb where eventTime>=? and alarmStatus='1' and flowTime is not null";
            } else if (startTime == null && endTime != null) {
                sql = "select * from omc_alert_information_tb where eventTime<=? and alarmStatus='1' and flowTime is not null";
            } else if (startTime != null && endTime != null) {
                sql = "select * from omc_alert_information_tb where eventTime>=? and eventTime<=? and alarmStatus='1' and flowTime is not null";
            }
        }else{
            if (startTime != null && endTime == null) {
                sql = "select * from omc_alert_information_tb where flowTime>=? and flowTime is not null";
            } else if (startTime == null && endTime != null) {
                sql = "select * from omc_alert_information_tb where flowTime<=? and flowTime is not null";
            } else if (startTime != null && endTime != null) {
                sql = "select * from omc_alert_information_tb where flowTime>=? and flowTime<=? and flowTime is not null";
            }
        }
        List<Object> params = new ArrayList<>();

        // 如果 startTime 不为 null，添加该条件
        if (startTime != null) {
            params.add(startTime);
        }

        // 如果 endTime 不为 null，添加该条件
        if (endTime != null) {
            params.add(endTime);
        }
        log.info("执行sql {}", sql);
        List<Alarm> alarms = jdbcTemplateOne.query(sql, params.toArray(), new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getInt("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getInt("alarmSeq"));
                alarm.setSpecificProblemID(rs.getString("specificProblemID"));
                alarm.setSpecificProblem(rs.getString("specificProblem"));
                alarm.setNeUID(rs.getString("neUID"));
                alarm.setNeName(rs.getString("neName"));
                alarm.setNeType(rs.getString("neType"));
                alarm.setObjectUID(rs.getString("objectUID"));
                alarm.setObjectName(rs.getString("objectName"));
                alarm.setObjectType(rs.getString("objectType"));
                alarm.setLocationInfo(rs.getString("locationInfo"));
                alarm.setAddInfo(rs.getString("addInfo"));
                return alarm;
            }
        });
        return alarms;
    }
    public void updateErrorStatus(){
        try {
            String sql = "update omc_alert_information_tb set status=false where status=true and flowTime is null";
            log.info("恢复异常重启告警，执行sql {}", sql);
            jdbcTemplateOne.update(sql);
        } catch (Exception e) {
            log.error("异常",e);
            throw e;
        }
    }

    @Override
    public void updateStatus() {
        try {
            String sql = "update omc_alert_information_tb set status=true where status=false";
            log.info("标记已经获取的告警信息，执行sql {}", sql);
            jdbcTemplateOne.update(sql);
        } catch (Exception e) {
            log.error("异常",e);
            throw e;
        }
    }
    @Override
    public void updateFlowTime(String formatTime,String alarmSeq){
        try {
            String sql = "update omc_alert_information_tb set flowTime=? where alarmSeq=?";
            log.info("记录发送告警信息时间，执行sql {}", sql);
            jdbcTemplateOne.update(sql,formatTime,alarmSeq);
        } catch (Exception e) {
            log.error("异常",e);
            throw e;
        }
    }

    @Override
    public void get4Gmdn(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when msisdn = '' or msisdn is null or msisdn = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_4glog_2c_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                 i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("4G手机号码填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("4G人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhsjrwrz");
            alarm.setObjectName("4G人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhsjrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，4G手机号码填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(1));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("4GMDN");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "4GMDN");
                    updateAlarmed("4GMDN", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "4GMDN");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void get4Gsourceip(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when ygwipdz = '' or ygwipdz is null or ygwipdz = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_4glog_2c_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("4G源公网IP填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("4G人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhsjrwrz");
            alarm.setObjectName("4G人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhsjrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，4G源公网IP填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(2));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("4GIP");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "4GIP");
                    updateAlarmed("4GIP", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "4GIP");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void get5Gmdn(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when msisdn = '' or msisdn is null or msisdn = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_5gsalog_2c_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("5G手机号码填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("5G人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhwjrwrz");
            alarm.setObjectName("5G人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhwjrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，5G手机号码填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(3));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("5GMDN");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "5GMDN");
                    updateAlarmed("5GMDN", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "5GMDN");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void get5Gsourceip(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when ygwipdz = '' or ygwipdz is null or ygwipdz = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_5gsalog_2c_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("5G源公网IP填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("5G人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhwjrwrz");
            alarm.setObjectName("5G人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhwjrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，5G源公网IP填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(4));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("5GIP");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "5GIP");
                    updateAlarmed("5GIP", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "5GIP");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void getHomeaccount(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when swzh = '' or swzh is null or swzh = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_homelog_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("家宽上网账号填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("家宽人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhjkrwrz");
            alarm.setObjectName("家宽人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhjkrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，家宽上网账号填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(5));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("HOMEACCOUNT");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "HOMEACCOUNT");
                    updateAlarmed("HOMEACCOUNT", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "HOMEACCOUNT");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void getHomesourceip(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when ygwipdz = '' or ygwipdz is null or ygwipdz = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_homelog_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("家宽源公网IP填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("家宽人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhjkrwrz");
            alarm.setObjectName("家宽人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhjkrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，家宽源公网IP填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(6));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("HOMEIP");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "HOMEIP");
                    updateAlarmed("HOMEIP", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "HOMEIP");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public void getIdcsourceip(String beforTime, String afterTime) {
        try {
            int maxId = getMaxId();
            String sql = "select round(sum(case when srcip = '' or srcip is null or srcip = 0  then 0 else 1 end )/count(1)*100,0) from mobile_db.orc_pv_log where partition_date >=? and  partition_date <=?;";
            Integer i=null;
            try {
//                i = jdbcTemplateTwo.queryForObject(sql, new Object[]{beforTime, afterTime}, Integer.class);
            }catch (Exception e){
                log.error("查询失败",e);
            }
            log.info("执行sql为{}-{}", sql, i);
            Alarm alarm = new Alarm();
            alarm.setAlarmSeq(maxId + 1);
            alarm.setAlarmId(String.valueOf(maxId + 1));
            alarm.setAlarmStatus(1);
            alarm.setAlarmTitle("IDC源公网IP填充率不足99%");
            alarm.setAlarmType("性能告警");
            alarm.setEventTime(beforTime);
            alarm.setNeName("IDC人网日志");
            alarm.setNeType("上网日志集中存储平台");
            alarm.setNeUID("lhidcrwrz");
            alarm.setObjectName("IDC人网日志");
            alarm.setObjectType("上网日志集中存储平台");
            alarm.setObjectUID("lhidcrwrz");
            alarm.setOrigSeverity("3");
            alarm.setSpecificProblem(beforTime + "-" + afterTime + " 时段，IDC源公网IP填充率" + i + "%");
            alarm.setSpecificProblemID(String.valueOf(7));
            i=100;
            if(i==null) return;
            if (i >= 99) {
                List<Integer> alarmSeq = getAlarmSeq("IDCIP");
                log.info("获取到的取消告警alarmseq {}",alarmSeq);
                for (Integer alarmId : alarmSeq) {
                    alarm.setAlarmSeq(++maxId);
                    alarm.setAlarmId(String.valueOf(alarmId));
                    alarm.setAlarmStatus(0);
                    addAlarm(alarm, "IDCIP");
                    updateAlarmed("IDCIP", String.valueOf(alarmId));
                    log.info("告警日志取消 {}-{}", alarmId, alarm);
                }
            } else {
                addAlarm(alarm, "IDCIP");
                log.info("告警日志{}", alarm);
            }
        } catch (Exception e) {
            log.error("异常",e);
        }
    }

    @Override
    public List<Integer> getAlarmSeq(String bussiness) {
        try {
            String sql = "select alarmSeq from omc_alert_information_tb where bussiness=? and alarmed=false and alarmStatus=1";
            String sql1 = "update omc_alert_information_tb set alarmed=true where bussiness=? and alarmed=false and alarmStatus=1";
            List<Integer> integers = jdbcTemplateOne.queryForList(sql, Integer.class, bussiness);
            log.info("执行sql{}-{}-{}", sql, bussiness,integers);
            jdbcTemplateOne.update(sql1,bussiness);
            log.info("执行sql{}-{}", sql1, bussiness);
            return integers;
        } catch (Exception e) {
            log.error("异常",e);
        }
        return new ArrayList<Integer>();
    }

    @Override
    public void updateAlarmed(String bussiness, String alarmId) {
        try {
            String sql = "update omc_alert_information_tb set alarmed=true where bussiness=? and alarmId=?";
            jdbcTemplateOne.update(sql, bussiness, alarmId);
            log.info("执行sql{}-{}-{}", sql, bussiness, alarmId);
        } catch (Exception e) {
            log.error("异常",e);
        }
    }
}
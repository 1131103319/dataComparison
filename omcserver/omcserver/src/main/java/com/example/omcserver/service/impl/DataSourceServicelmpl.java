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
    public void addUser(Alarm alarm) {
        String sql = "INSERT INTO omc_alert_information_tb (alarmTitle,alarmStatus,alarmType,origSeverity,eventTime,alarmId,alarmSeq,specificProblemID,specificProblem,neUID,neName,neType,objectUID,objectName,objectType,locationInfo,addInfo,status)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        //2.调用 update语句，执行方法
        jdbcTemplateOne.update(sql, alarm.getAlarmTitle(), alarm.getAlarmStatus(), alarm.getAlarmType(), alarm.getOrigSeverity(), alarm.getEventTime(), alarm.getAlarmId(),alarm.getAlarmSeq(), alarm.getSpecificProblemID(), alarm.getSpecificProblem(), alarm.getNeUID(), alarm.getObjectName(), alarm.getObjectType(), alarm.getLocationInfo(), alarm.getAddInfo(), "false");
    }

    @Override
    public List<Alarm> listAlarm() {
        String sql = "select * from omc_alert_information_tb where status=false";
        List<Alarm> alarms = jdbcTemplateOne.query(sql, new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getString("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getString("alarmSeq"));
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
        String sql = "select * from omc_alert_information_tb where alarmSeq>=?";

        List<Alarm> alarms = jdbcTemplateOne.query(sql, new Object[]{alarmSeq}, new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getString("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getString("alarmSeq"));
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
    public List<Alarm> listAlarm(String startTime, String endTime) {
        String sql = "";
        if("".equals(startTime)){
            startTime=null;
        }
        if("".equals(endTime)){
            endTime=null;
        }
        if (startTime != null && endTime == null) {
            sql = "select * from omc_alert_information_tb where eventTime>=?";
        } else if (startTime == null && endTime != null) {
            sql = "select * from omc_alert_information_tb where eventTime<=?";
        } else if (startTime != null && endTime != null) {
            sql = "select * from omc_alert_information_tb where eventTime>=? and eventTime<=?";
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

        List<Alarm> alarms = jdbcTemplateOne.query(sql,params.toArray(), new RowMapper<Alarm>() {
            @Override
            public Alarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                Alarm alarm = new Alarm();
                alarm.setAlarmTitle(rs.getString("alarmTitle"));
                alarm.setAlarmStatus(rs.getString("alarmStatus"));
                alarm.setAlarmType(rs.getString("alarmType"));
                alarm.setOrigSeverity(rs.getString("origSeverity"));
                alarm.setEventTime(rs.getString("eventTime"));
                alarm.setAlarmId(rs.getString("alarmId"));
                alarm.setAlarmSeq(rs.getString("alarmSeq"));
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

    public Integer updateStatus() {
        String sql = "update omc_alert_information_tb set status=true where status=false";
        return jdbcTemplateOne.update(sql);
    }
}
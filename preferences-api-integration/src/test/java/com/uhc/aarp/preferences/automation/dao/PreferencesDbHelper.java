package com.uhc.aarp.preferences.automation.dao;


import com.uhc.aarp.preferences.automation.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

//import com.uhc.aarp.preferencecenter.util.PreferenceCenterUtil;
@Slf4j
public class PreferencesDbHelper {


    public static String retrieveIndividualID(String oleRefId) throws SQLException, ClassNotFoundException {

        List<HashMap<String, String>> rows = DbUtils.compasQuery(String.format("select a.individual_id from ole_application a, application b where a.application_id = b.application_id and ole_reference_identifier = '%s'", oleRefId));
        return rows.get(0).get("INDIVIDUAL_ID");

    }


    public static void insertPreferenceforMember(String individualId) throws SQLException, ClassNotFoundException {

        List<HashMap<String, String>> rows = DbUtils.acesQuery("select smart_person_id from is_customerregistry.registry where compas_individual_id is null and rownum=1");
        String smartPersonId = rows.get(0).get("SMART_PERSON_ID");
        String updateQuery = String.format("update is_customerregistry.registry set compas_individual_id = %s " +
                " where smart_person_id = %s", individualId, smartPersonId);
        log.debug(updateQuery);
        DbUtils.execute(updateQuery);


    }
}

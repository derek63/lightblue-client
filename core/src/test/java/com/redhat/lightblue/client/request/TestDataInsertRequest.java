package com.redhat.lightblue.client.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.redhat.lightblue.client.MongoExecution;
import com.redhat.lightblue.client.MongoExecution.WriteConcern;
import com.redhat.lightblue.client.Projection;
import com.redhat.lightblue.client.request.data.DataInsertRequest;

public class TestDataInsertRequest {

    @Test
    public void testInsertToString() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.returns(Arrays.asList(Projection.includeField("*")));
        request.create("");

        assertEquals(request.toString(), "PUT /insert/fake, body: {\"projection\":{\"field\":\"*\",\"include\":true,\"recursive\":false},\"data\":\"\"}");
    }

    @Test
    public void testProjectionsAsList() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.returns(Arrays.asList(Projection.includeField("*")));
        request.create("");

        assertTrue(request.getBody(), request.getBody().contains(
                "\"projection\":{\"field\":\"*\",\"include\":true,\"recursive\":false}"));
    }

    @Test
    public void testProjectionsAsArray() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.returns(Projection.includeField("*"));
        request.create("");

        assertTrue(request.getBody(), request.getBody().contains(
                "\"projection\":{\"field\":\"*\",\"include\":true,\"recursive\":false}"));
    }

    @Test
    public void testExecutionReadPreference() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.execution(MongoExecution.withReadPreference(MongoExecution.ReadPreference.primaryPreferred));
        request.create("");

        assertTrue(request.getBody(), request.getBody().contains(
                "\"execution\":{\"readPreference\":\"primaryPreferred\"}"));
    }

    @Test
    public void testExecutionWriteConcern() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.execution(MongoExecution.withWriteConcern(WriteConcern.majority));
        request.create("");

        assertTrue(request.getBody(), request.getBody().contains(
                "\"execution\":{\"writeConcern\":\"majority\"}"));
    }

    @Test
    public void testExecutionMaxQueryTimeMS() {
        DataInsertRequest request = new DataInsertRequest("fake");
        request.execution(MongoExecution.withMaxQueryTimeMS(1000));
        request.create("");

        assertTrue(request.getBody(), request.getBody().contains(
                "\"execution\":{\"maxQueryTimeMS\":1000"));
    }

}

package com.example.patrickwerner.hapitest;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import org.hl7.fhir.dstu3.model.Patient;


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.client.ServerValidationModeEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //very quick&dirty
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FhirContext ctx = FhirContext.forDstu3();
        String serverBase = "http://fhirtest.uhn.ca/baseDstu3";
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        // Perform a search
        org.hl7.fhir.dstu3.model.Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value("duck"))
                .returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
                .execute();

        Log.i("testtag", "Found " + results.getEntry().size() + " patients named 'duck'");
    }

}

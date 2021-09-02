/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.paho.android.sample.activity;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

//Actual Async Task Class
public class WebApiAsyncTask extends AsyncTask<String, String, String> {

  protected void onPreExecute() {
      super.onPreExecute();
  }

  protected String doInBackground(String... params) {
    String JsonResponse = null;
    String JsonDATA = params[0];
    String webApiLink = params[1];
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    try {
      URL url = new URL(webApiLink);
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setDoOutput(true);
      // is output buffer writter
      urlConnection.setRequestMethod("POST");
      urlConnection.setRequestProperty("Content-Type", "application/json");
      urlConnection.setRequestProperty("Accept", "application/json");
      //set headers and method
      Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
      writer.write(JsonDATA);
      // json data
      writer.close();
      InputStream inputStream = urlConnection.getInputStream();
      //input stream
      StringBuffer buffer = new StringBuffer();
      if (inputStream == null) {
        // Nothing to do.
        return null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));

      String inputLine;
      while ((inputLine = reader.readLine()) != null)
        buffer.append(inputLine + "\n");
      if (buffer.length() == 0) {
        // Stream was empty. No point in parsing.
        return null;
      }
      JsonResponse = buffer.toString();
      //response data
      try {
        //send to post execute
        return JsonResponse;
      } catch (Exception e) {
        e.printStackTrace();
      }

      return null;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
        }
      }
    }
    return null;
  }

  @Override
  protected void onPostExecute(String result) {
    super.onPostExecute(result);
    //Do something with result
  }
}
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package edu.usc.sunset.burgess.tika;

//JDK imports
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import java.io.IOException;
import java.io.InputStream;
/*
 * Test cases to exercise the {@link EnviHeaderParser}.
 * 
 */
public class EnviHeaderParserTest
{
    @Test
    public void testParseGlobalMetadata() throws Exception {
        if(System.getProperty("java.version").startsWith("1.5")) {
            return;
        }

        Parser parser = new EnviHeaderParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        InputStream stream = EnviHeaderParser.class
                .getResourceAsStream("/test-documents/envi_test_header.hdr");
        assertNotNull("Test ENVI file not found", stream);
        try {
            parser.parse(stream, handler, metadata, new ParseContext());
        } finally {
            stream.close();
        }

        // Check content of test file
        String content = handler.toString();
        assertTrue(content.contains("ENVI"));
        assertTrue(content.contains("samples = 2400"));
        assertTrue(content.contains("lines   = 2400"));
        assertTrue(content.contains("bands   = 7"));
        assertTrue(content.contains("header offset = 0"));
        assertTrue(content.contains("file type = ENVI Standard"));
        assertTrue(content.
        contains("projection info = {16, 6371007.2, 0.000000, 0.0, 0.0, Sinusoidal, units=Meters}"));
    }
}

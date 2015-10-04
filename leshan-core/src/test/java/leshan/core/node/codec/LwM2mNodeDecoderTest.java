/*
 * Copyright (c) 2013, Sierra Wireless
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of {{ project }} nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package leshan.core.node.codec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Date;

import leshan.core.node.LwM2mObjectInstance;
import leshan.core.node.LwM2mPath;
import leshan.core.node.LwM2mResource;
import leshan.core.node.Value.DataType;
import leshan.core.node.codec.InvalidValueException;
import leshan.core.node.codec.LwM2mNodeDecoder;
import leshan.core.objectspec.Resources;
import leshan.core.request.ContentFormat;
import leshan.tlv.Tlv;
import leshan.tlv.Tlv.TlvType;
import leshan.tlv.TlvEncoder;
import leshan.util.Charsets;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link LwM2mNodeDecoder}
 */
public class LwM2mNodeDecoderTest {

    @BeforeClass
    public static void loadResourceSpec() {
        Resources.load();
    }

    @Test
    public void text_manufacturer_resource() throws InvalidValueException {
        String value = "MyManufacturer";
        LwM2mResource resource = (LwM2mResource) LwM2mNodeDecoder.decode(value.getBytes(Charsets.UTF_8),
                ContentFormat.TEXT, new LwM2mPath(3, 0, 0));

        assertEquals(0, resource.getId());
        assertFalse(resource.isMultiInstances());
        assertEquals(DataType.STRING, resource.getValue().type);
        assertEquals(value, resource.getValue().value);
    }

    @Test
    public void text_battery_resource() throws InvalidValueException {
        LwM2mResource resource = (LwM2mResource) LwM2mNodeDecoder.decode("100".getBytes(Charsets.UTF_8),
                ContentFormat.TEXT, new LwM2mPath(3, 0, 9));

        assertEquals(9, resource.getId());
        assertFalse(resource.isMultiInstances());
        assertEquals(DataType.INTEGER, resource.getValue().type);
        assertEquals(100, ((Number) resource.getValue().value).intValue());
    }

    @Test
    public void tlv_manufacturer_resource() throws InvalidValueException {
        String value = "MyManufacturer";
        byte[] content = TlvEncoder.encode(new Tlv[] { new Tlv(TlvType.RESOURCE_VALUE, null, value.getBytes(), 0) })
                .array();
        LwM2mResource resource = (LwM2mResource) LwM2mNodeDecoder.decode(content, ContentFormat.TLV, new LwM2mPath(3,
                0, 0));

        assertEquals(0, resource.getId());
        assertFalse(resource.isMultiInstances());
        assertEquals(value, resource.getValue().value);
    }

    @Test
    public void tlv_device_object_instance0() throws InvalidValueException {
        // tlv content for instance 0 of device object
        byte[] content = new byte[] { -56, 0, 20, 79, 112, 101, 110, 32, 77, 111, 98, 105, 108, 101, 32, 65, 108, 108,
                                105, 97, 110, 99, 101, -56, 1, 22, 76, 105, 103, 104, 116, 119, 101, 105, 103, 104,
                                116, 32, 77, 50, 77, 32, 67, 108, 105, 101, 110, 116, -56, 2, 9, 51, 52, 53, 48, 48,
                                48, 49, 50, 51, -61, 3, 49, 46, 48, -122, 6, 65, 0, 1, 65, 1, 5, -120, 7, 8, 66, 0, 14,
                                -40, 66, 1, 19, -120, -121, 8, 65, 0, 125, 66, 1, 3, -124, -63, 9, 100, -63, 10, 15,
                                -63, 11, 0, -60, 13, 81, -126, 66, -113, -58, 14, 43, 48, 50, 58, 48, 48, -63, 15, 85 };

        LwM2mObjectInstance oInstance = (LwM2mObjectInstance) LwM2mNodeDecoder.decode(content, ContentFormat.TLV,
                new LwM2mPath(3, 0));

        assertEquals(0, oInstance.getId());

        assertEquals("Open Mobile Alliance", (String) oInstance.getResources().get(0).getValue().value);
        assertEquals("Lightweight M2M Client", (String) oInstance.getResources().get(1).getValue().value);
        assertEquals("345000123", (String) oInstance.getResources().get(2).getValue().value);
        assertEquals("1.0", (String) oInstance.getResources().get(3).getValue().value);
        assertNull(oInstance.getResources().get(4));
        assertNull(oInstance.getResources().get(5));
        assertEquals(2, oInstance.getResources().get(6).getValues().length);
        assertEquals(1, ((Number) oInstance.getResources().get(6).getValues()[0].value).intValue());
        assertEquals(5, ((Number) oInstance.getResources().get(6).getValues()[1].value).intValue());
        assertEquals(3800, ((Number) oInstance.getResources().get(7).getValues()[0].value).intValue());
        assertEquals(5000, ((Number) oInstance.getResources().get(7).getValues()[1].value).intValue());
        assertEquals(125, ((Number) oInstance.getResources().get(8).getValues()[0].value).intValue());
        assertEquals((int) 900, oInstance.getResources().get(8).getValues()[1].value);
        assertEquals(100, ((Number) oInstance.getResources().get(9).getValue().value).intValue());
        assertEquals(15, ((Number) oInstance.getResources().get(10).getValue().value).intValue());
        assertEquals(0, ((Number) oInstance.getResources().get(11).getValue().value).intValue());
        assertNull(oInstance.getResources().get(12));
        assertEquals(new Date(1367491215000L), (Date) oInstance.getResources().get(13).getValue().value);
        assertEquals("+02:00", (String) oInstance.getResources().get(14).getValue().value);
        assertEquals("U", (String) oInstance.getResources().get(15).getValue().value);
    }

    @Test
    public void tlv_power_source__array_values() throws InvalidValueException {
        byte[] content = new byte[] { 65, 0, 1, 65, 1, 5 };

        LwM2mResource resource = (LwM2mResource) LwM2mNodeDecoder.decode(content, ContentFormat.TLV, new LwM2mPath(3,
                0, 6));

        assertEquals(6, resource.getId());
        assertEquals(2, resource.getValues().length);
        assertEquals(1, ((Number) resource.getValues()[0].value).intValue());
        assertEquals(5, ((Number) resource.getValues()[1].value).intValue());
    }

    @Test
    public void tlv_power_source__multiple_resource() throws InvalidValueException {
        // this content (a single TLV of type 'multiple_resource' containing the values)
        // is probably not compliant with the spec but it should be supported by the server
        byte[] content = new byte[] { -122, 6, 65, 0, 1, 65, 1, 5 };

        LwM2mResource resource = (LwM2mResource) LwM2mNodeDecoder.decode(content, ContentFormat.TLV, new LwM2mPath(3,
                0, 6));

        assertEquals(6, resource.getId());
        assertEquals(2, resource.getValues().length);
        assertEquals(1, ((Number) resource.getValues()[0].value).intValue());
        assertEquals(5, ((Number) resource.getValues()[1].value).intValue());
    }
}

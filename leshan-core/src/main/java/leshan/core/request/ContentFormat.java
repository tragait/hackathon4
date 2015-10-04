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
package leshan.core.request;

/**
 * Data format defined by the LWM2M specification
 */
public enum ContentFormat {

    // TODO: update media type codes once they have been assigned by IANA
    LINK("application/link-format", 40), TEXT("application/vnd.oma.lwm2m+text", 1541), TLV(
            "application/vnd.oma.lwm2m+tlv", 1542), JSON("application/vnd.oma.lwm2m+json", 1543), OPAQUE(
            "application/vnd.oma.lwm2m+opaque", 1544);

    private final String mediaType;
    private final int code;

    private ContentFormat(String mediaType, int code) {
        this.mediaType = mediaType;
        this.code = code;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public int getCode() {
        return this.code;
    }

    /**
     * Find the {@link ContentFormat} for the given media type (<code>null</code> if not found)
     */
    public static ContentFormat fromMediaType(String mediaType) {
        for (ContentFormat t : ContentFormat.values()) {
            if (t.getMediaType().equals(mediaType)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Finds the {@link ContentFormat} for a given media type code.
     *
     * @return the media type or <code>null</code> if the given code is unknown
     */
    public static ContentFormat fromCode(int code) {
        for (ContentFormat t : ContentFormat.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
}

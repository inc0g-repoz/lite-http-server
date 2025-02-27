package com.github.inc0grepoz.http.server.response;

public enum ResponseContentType {

    APP_JAVA_ARCHIVE("application/java-archive"),
    APP_EDI_X12("application/EDI-X12"),
    APP_EDI_FACT("application/EDIFACT"),
    APP_JAVASCRIPT("application/javascript (\"obsolete\")"),
    APP_OCTET_STREAM("application/octet-stream"),
    APP_OGG("application/ogg"),
    APP_PDF("application/pdf"),
    APP_XHTML_XML("application/xhtml+xml"),
    APP_X_SHOCKWAVE_FLASH("application/x-shockwave-flash"),
    APP_JSON("application/json"),
    APP_LD_JSON("application/ld+json"),
    APP_XML("application/xml"),
    APP_ZIP("application/zip"),
    APP_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_X_MS_WMA("audio/x-ms-wma"),
    AUDIO_VND_RN_REALAUDIO("audio/vnd.rn-realaudio"),
    AUDIO_X_WAV("audio/x-wav"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_VND_MICROSOFT_ICON("image/vnd.microsoft.icon"),
    IMAGE_X_ICON("image/x-icon"),
    IMAGE_VND_DJVU("image/vnd.djvu"),
    IMAGE_SVG_XML("image/svg+xml"),
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    MULTIPART_RELATED("multipart/related (\"using by MHTML (\"HTML mail\").\")"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_CSS("text/css"),
    TEXT_CSV("text/csv"),
    TEXT_EVENT_STREAM("text/event-stream"),
    TEXT_HTML("text/html"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    VIDEO_MPEG("video/mpeg"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime"),
    VIDEO_X_MS_WMV("video/x-ms-wmv"),
    VIDEO_X_MSVIDEO("video/x-msvideo"),
    VIDEO_X_FLV("video/x-flv"),
    VIDEO_WEBM("video/webm"),
    APP_VND_ANDROID_PACKAGE_ARCHIVE("application/vnd.android.package-archive"),
    APP_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
    APP_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    APP_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"),
    APP_VND_OASIS_OPENDOCUMENT_GRAPHICS("application/vnd.oasis.opendocument.graphics"),
    APP_VND_MS_EXCEL("application/vnd.ms-excel"),
    APP_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    APP_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
    APP_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    APP_MSWORD("application/msword"),
    APP_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    APP_VND_MOZILLA_XUL_XML("application/vnd.mozilla.xul+xml");

    private final String string;

    ResponseContentType(String string)
    {
        this.string = string;
    }

    @Override
    public String toString()
    {
        return string;
    }

}

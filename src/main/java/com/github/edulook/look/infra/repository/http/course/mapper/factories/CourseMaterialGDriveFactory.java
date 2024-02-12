package com.github.edulook.look.infra.repository.http.course.mapper.factories;

import com.github.edulook.look.core.data.Option;
import com.github.edulook.look.core.data.Typename;
import com.google.api.services.classroom.model.Material;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;

@Slf4j
public class CourseMaterialGDriveFactory implements AbstractCourseMaterialFactory {

    private  static List<String> videoExtension = List.of(
        "mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpg", "mpeg", "3gp", "m4v", "ts", "vob",
        "m2ts", "mts", "divx", "rmvb", "ogv", "mpv", "mxf", "asf", "f4v", "rm", "mp2", "mp3", "m1v", "m2v", "mpe",
        "mpeg1", "mpeg2", "mpeg4", "mpg2", "mpg4", "ogm", "qt", "swf", "vcd", "xvid", "3g2", "3gp2", "3gpp", "amv",
        "dpg", "dvd", "h264", "mjpeg", "mod"
    );

    private static List<String> imageExtensions = List.of(
        "jpg", "jpeg", "png", "gif", "bmp", "svg", "webp", "tiff", "ico", "raw", "heif", "eps", "ai", "psd",
        "indd", "cdr", "wmf", "emf", "pcx", "pict", "jp2", "jxr", "hdp", "wdp", "dds", "dng", "cr2", "nef", "orf",
        "arw", "rw2", "raf", "sr2", "pef", "x3f", "mrw", "srf", "erf", "mef", "mos", "crw", "kdc", "dcr", "ptx",
        "pxn", "fff", "3fr", "mfw", "rwl", "srw", "spx", "rwz", "r3d", "cap", "bay", "iiq", "fff", "jpe", "jif",
        "jfif", "jfi", "jng", "jbig", "jbg", "jxr", "hdp", "wdp", "cur", "ani", "icns", "ico", "icn", "pic", "pct",
        "pnt", "pntg", "mac", "qtif", "qif", "lbm", "iff", "ilbm", "pbm", "pgm", "ppm", "pnm", "ras", "sun", "tga",
        "tpic", "vda", "icb", "vst", "wbmp", "xbm", "xpm", "xwd", "yuv", "sgi", "rgb", "rgba", "bw", "int", "inta",
        "vicar", "fits", "ftc", "fpx", "mic", "mpo", "mng", "j2k", "jpf", "jpx", "jpm", "mj2", "wdp", "jxr", "hdp",
        "dpx", "cin", "exr", "hdr", "rgbe", "xyze", "dcm", "dicom", "dic", "dcm30", "dcm40", "dcm50", "dcm60", "dcm70",
        "dcm80", "dcm90", "dcm100", "dcm200", "dcm300", "dcm400", "dcm500", "dcm600", "dcm700", "dcm800", "dcm900",
        "dcm1000", "dcm2000", "dcm3000", "dcm4000", "dcm5000", "dcm6000", "dcm7000", "dcm8000", "dcm9000", "dcm10000"
    );

    @Override
    public com.github.edulook.look.core.model.Material create(Material source) {
        try {
            if (source == null)
                throw new IllegalArgumentException("material can't be null");

            var sharedDriveFile = source.getDriveFile();
            var file = sharedDriveFile.getDriveFile();

            var filename = file.getTitle();
            var filetype = getFiletype(filename);


            var option = filetype.equalsIgnoreCase(Typename.PDF)
                    ? Option.withDefaults()
                    : null;

            return com.github.edulook.look.core.model.Material
                .builder()
                .id(hash256(file.getAlternateLink()))
                .name(normalizeFilename(file.getTitle()))
                .originLink(file.getAlternateLink())
                .previewLink(file.getThumbnailUrl())
                .type(filetype)
                .description(file.getTitle())
                .option(option)
                .build();
        } catch (Exception e) {
            log.error("error:: ", e);
            return null;
        }
    }


    private String getFiletype(String filename) {
        if(filename == null)
            return Typename.NONE;

        var slices = filename.split("[.]");
        if(slices.length < 2)
            return Typename.FILE;

        var extension  = slices[slices.length - 1 ].toLowerCase(Locale.ROOT);
        if(extension.equalsIgnoreCase("pdf"))
            return Typename.PDF;
        else if (videoExtension.contains(extension))
            return Typename.VIDEO;
        else if (imageExtensions.contains(extension))
            return Typename.IMAGE;

        return Typename.FILE;
    }
}

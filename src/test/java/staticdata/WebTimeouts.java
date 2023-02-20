package staticdata;

import java.time.Duration;

public class WebTimeouts {

    public static Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(20);
    public static Duration SCRIPT_TIMEOUT = Duration.ofSeconds(20);
    public static Duration IMPLICIT_TIMEOUT = Duration.ofSeconds(20);
}

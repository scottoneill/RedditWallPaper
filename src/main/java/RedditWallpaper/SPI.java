package RedditWallpaper;

import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

public interface SPI extends StdCallLibrary {
    long SPI_SETDESKWALLPAPER = 20;
    long SPIF_UPDATEINIFILE = 0x01;
    long SPIF_SENDWININICHANGE = 0x02;

    SPI INSTANCE = (SPI) Native.loadLibrary(
            "user32",
            SPI.class,
            W32APIOptions.DEFAULT_OPTIONS);

    boolean SystemParametersInfo(
        WinDef.UINT_PTR uiAction,
        WinDef.UINT_PTR uiParam,
        String pvParam,
        WinDef.UINT_PTR fWinIni        
    );
}

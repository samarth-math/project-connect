package fileSending;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWalker {

    public void walk( String path, Socket socket ) throws IOException {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                Path p = Paths.get(f.getAbsoluteFile().toString());
            	Sender.sendFile(socket, p);
                walk( f.getAbsolutePath(), socket );
            }
            else {
            	Path p = Paths.get(f.getAbsoluteFile().toString());
            	Sender.sendFile(socket, p);
            }
        }
    }

}

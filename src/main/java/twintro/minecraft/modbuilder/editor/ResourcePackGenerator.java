package twintro.minecraft.modbuilder.editor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ResourcePackGenerator {
	public void generate() throws IOException{
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(Editor.outputDir));
		
		addFile(zos, new File(Editor.resourcePackFolderDir));
		
		zos.close();
	}
	
	private void addFile(ZipOutputStream zos, File file) throws IOException{
		BufferedInputStream stream = null;
		try{
			String name = file.getPath().replace("\\", "/").replace(Editor.resourcePackFolderDir, "");
			
			if (file.isDirectory()){
				if (!name.isEmpty()){
					if (!name.endsWith("/"))
						name += "/";
					ZipEntry entry = new ZipEntry(name);
					entry.setTime(file.lastModified());
					zos.putNextEntry(entry);
					zos.closeEntry();
				}
				for (File nestedFile : file.listFiles()){
					addFile(zos, nestedFile);
				}
				return;
			}
			
			ZipEntry entry = new ZipEntry(name);
			entry.setTime(file.lastModified());
			zos.putNextEntry(entry);
			stream = new BufferedInputStream(new FileInputStream(file));
			
			byte[] buffer = new byte[1024];
			while (true){
				int count = stream.read(buffer);
				if (count == -1)
					break;
				zos.write(buffer, 0, count);
			}
			zos.closeEntry();
		}
		finally{
			if (stream != null){
				stream.close();
			}
		}
	}
}

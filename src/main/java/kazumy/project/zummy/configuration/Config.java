package kazumy.project.zummy.configuration;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kazumy.project.zummy.MainZummy;
import lombok.Data;
import lombok.val;

@SuppressWarnings("all")
@Data(staticConstructor = "of")
public class Config {
	
	private static final String PATH = new File("").getAbsolutePath(); 
	
	private final MainZummy instance;
	private JSONObject json;
	
	public Config createConfig() {
		val file = new File(PATH, "config.json");
		
		if (!file.exists())
			try {
				file.createNewFile();
				writeConfig(file);
				
			} catch (IOException e) {
				System.out.println("[Zummy] Falha ao criar o arquivo de configuração.");
			}
		
		return this;
	}
	
	private void writeConfig(File file) {
		json = new JSONObject();
		
		json.put("token", "Seu-Token");
		json.put("prefix", "-");
		json.put("status", "[%s] para informações");
		
		val array = new JSONArray();
		array.addAll(Arrays.asList("Financeiro","Técnico","Dúvidas"));
		
		val categories = new JSONObject();
		categories.put("categories", array.toJSONString());
		
		json.put("ticket", categories);
		
	    val path = Paths.get("./config.json");
	    val config = json.toJSONString().getBytes();
	    
	    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path))) {
	      out.write(config, 0, config.length);
	    } catch (IOException x) {
	    	System.out.println("[Zummy] Falha ao escrever o arquivo de configuração.");
	    }
	}

	public Config readConfig() {
		val path = Paths.get("./config.json");
		
		try (InputStream in = Files.newInputStream(path);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			    String line = null;
			    while ((line = reader.readLine()) != null) {
			        System.out.println("Arquivo de configuração: " + line);
			        
					val parser = new JSONParser();
					json = (JSONObject) parser.parse(line);
			    }
			} catch (IOException | ParseException e) {
				System.out.println("[Zummy] Falha ao ler o arquivo de configuração.");
			}
		
		return this;
	}
}

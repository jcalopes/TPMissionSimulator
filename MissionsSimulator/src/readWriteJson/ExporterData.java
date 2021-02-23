/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readWriteJson;

import exceptions.NoManualSimulationsException;
import exceptions.NullElementValueException;
import interfaces.IDivision;
import interfaces.IManualSimulation;
import interfaces.IVersion;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import missions.Division;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 */
public class ExporterData {
    
    public static String exportSimulacoesManuais(String codMission, IVersion version) 
            throws NullElementValueException, NoManualSimulationsException, IOException {
        if(codMission==null || version==null)
            throw new NullElementValueException("Invalid Arguments");
        if(version.getNumManualSimulations()==0){
            throw new NoManualSimulationsException("There are not manual simulations");
        }
        JSONObject jDoc = new JSONObject();
        jDoc.put("CodMissao", codMission);
        jDoc.put("Versao", version.getCodVersion());
        jDoc.put("NumSimulacoesManuais", version.getNumManualSimulations());
        JSONArray jSimulacoes=new JSONArray();
        
        Iterator<IManualSimulation> simulacoes=version.getManualSimulation().iterator();
        
        while(simulacoes.hasNext()){
            IManualSimulation current=simulacoes.next();
            JSONObject jSimulacaoManual=new JSONObject();
            jSimulacaoManual.put("Sucesso", current.isSuccess());
            jSimulacaoManual.put("PontosVida",current.getRemainingLife());
            if(!current.getPowerUps().hasRecoverDamage()){
                jSimulacaoManual.put("PowerUp", "Recover Last Damage");
            }

            if (!current.getPowerUps().hasRestoreLife()) {
                jSimulacaoManual.put("PowerUp", "Restore Life");
            }    
            
            JSONArray trajetoPercorrido=new JSONArray();
            Iterator<Division> divisoes=current.getPath().iterator();
            
            while(divisoes.hasNext()){
                trajetoPercorrido.add(divisoes.next().getName());
            }
            jSimulacaoManual.put("Trajeto",trajetoPercorrido);
            jSimulacoes.add(jSimulacaoManual);
        }

        jDoc.put("Simulacoes", jSimulacoes);

        try (FileWriter file = new FileWriter("Relatorios/" + "CodMissao_"+codMission+
                "Versao_"+version.getCodVersion()+".json")) {
            file.write(jDoc.toJSONString());
        }
        String result = jDoc.toJSONString();
        return result;
    }
}

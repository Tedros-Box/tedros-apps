package org.tedros.tools.test;

import java.util.concurrent.TimeUnit;

import org.tedros.it.tools.evidence.component.EvidenceScheduler;

public class MainTest {

    public static void main(String[] args) {
        // Instancia o Agendador de Evidências
        EvidenceScheduler scheduler = new EvidenceScheduler();

        System.out.println("--- Teste de Monitoramento de Evidências ---");
        System.out.println("Aguarde a inicialização do scheduler...");

        // 1. INICIA O MONITORAMENTO
        scheduler.startMonitoring();

        // 2. PERMITE QUE O TESTE EXECUTE POR UM TEMPO (e.g., 30 segundos)
        // Em uma aplicação JavaFX real, isso seria controlado pela UI.
        int durationSeconds = 30;
        
        try {
            System.out.println("O monitoramento será executado por " + durationSeconds + " segundos. Tente focar nas janelas alvo (IntelliJ IDEA, Microsoft Teams, etc.)");
            
            // Pausa a Thread principal, permitindo que o scheduler em background execute
            TimeUnit.SECONDS.sleep(durationSeconds);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("O teste foi interrompido.");
        }

        // 3. PARA O MONITORAMENTO
        scheduler.stopMonitoring();

        System.out.println("--- Fim do Teste. Verifique o diretório do projeto para os arquivos 'evidence_*.png' ---");
    }
}

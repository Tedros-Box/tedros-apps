# 1. Definição da lista de projetos
$projects = @(
	[PSCustomObject]@{ ID = 1; Name = "Tedros Box";        Path = "D:\GitHub\Tedros-Box\Tedros\tedrosbox" }
	[PSCustomObject]@{ ID = 2; Name = "App Extensions";    Path = "D:\GitHub\Tedros-Box\tedros-apps\app-extensions" }
    [PSCustomObject]@{ ID = 3; Name = "App Person";        Path = "D:\GitHub\Tedros-Box\tedros-apps\app-person" }
    [PSCustomObject]@{ ID = 4; Name = "App Stock";         Path = "D:\GitHub\Tedros-Box\tedros-apps\app-stock" }
    [PSCustomObject]@{ ID = 5; Name = "App Services";      Path = "D:\GitHub\Tedros-Box\tedros-apps\app-services" }
    [PSCustomObject]@{ ID = 6; Name = "App iFood Tools";   Path = "D:\GitHub\Tedros-Box\tedros-apps\app-ifood-tools" }
    [PSCustomObject]@{ ID = 7; Name = "App IT Support";    Path = "D:\GitHub\Tedros-Box\tedros-apps\app-itsupport-tools" }
    [PSCustomObject]@{ ID = 8; Name = "App Samples";       Path = "D:\GitHub\Tedros-Box\tedros-apps\app-samples" }
)

Write-Host "--- Interface de Seleção Aberta ---" -ForegroundColor Cyan

# 2. Abre a interface e aguarda o botão OK
# DICA: Use CTRL para selecionar múltiplos ou SHIFT para intervalos
$selectedProjects = $projects | Out-GridView -Title "Selecione e clique em OK no canto inferior direito" -PassThru

if ($null -eq $selectedProjects) {
    Write-Host "Nenhum projeto foi selecionado ou a janela foi fechada sem clicar em OK." -ForegroundColor Red
} else {
    # Filtra e ordena baseado na lista original para garantir a sequência correta
    $idsSelecionados = $selectedProjects.ID
    $orderedSelection = $projects | Where-Object { $idsSelecionados -contains $_.ID }

    Write-Host ("`nIniciando build de " + $orderedSelection.Count + " projeto(s)...") -ForegroundColor Green

    foreach ($project in $orderedSelection) {
        Write-Host "`n>>> [INICIANDO] $($project.Name)" -ForegroundColor Black -BackgroundColor Yellow
        Write-Host "Pasta: $($project.Path)" -ForegroundColor Gray

        if (Test-Path $project.Path) {
            Push-Location $project.Path
            
            # Executa o Maven e exibe o log em tempo real
            mvn clean install -DskipTests
            
            if ($LASTEXITCODE -ne 0) {
                Write-Host "`n[ERRO] O Maven falhou no projeto $($project.Name). O script foi interrompido." -ForegroundColor White -BackgroundColor Red
                Pop-Location
                break
            }
            
            Pop-Location
            Write-Host ">>> [SUCESSO] $($project.Name) finalizado." -ForegroundColor Green
        } else {
            Write-Host "[ERRO] Caminho não encontrado: $($project.Path)" -ForegroundColor Red
        }
    }
}

Write-Host "`n--- Processo Finalizado ---" -ForegroundColor Cyan
Write-Host "Pressione qualquer tecla para sair..."
$null = [Console]::ReadKey()
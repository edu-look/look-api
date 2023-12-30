# GCP
- [Novo projeto](#novo-projeto)
- [Classroom API](#classroom-api)
- [Autenticação](#autenticação)
    - [Tela de concentimento](#tela-de-concentimento)
    - [Credenciais Web](#credenciais-web)
    - [Credenciais Backend](#credenciais-backend)
    - [Look service](#look-service)
## Novo projeto
- Crie um novo projeto no GCP e configure
![](screenshot/gcp/01.png)
![](screenshot/gcp/02.png)
![](screenshot/gcp/03.png)

## Classroom e Drive API
- É necessário ativar a API do classroom para o projeto. ***Por favor faça o mesmo processo para ativar o serviço do Drive***.
![](screenshot/gcp/04.png)
![](screenshot/gcp/05.png)
![](screenshot/gcp/06.png)
![](screenshot/gcp/07.png)
![](screenshot/gcp/08.png)
![](screenshot/gcp/09.png)
## Autenticação
### Tela de concentimento
- Configurando a tela de concentimento de autorização de acesso para configurar o OAuth2 do Look.
![](screenshot/gcp/11.png)
![](screenshot/gcp/12.png)
![](screenshot/gcp/13.png)
![](screenshot/gcp/14.png)
![](screenshot/gcp/15.png)
![](screenshot/gcp/16.png)
- Adicione os escopos ***email***, ***open id*** e ***profile***.
![](screenshot/gcp/17.png)
- Adicione os usuários que poderam testar a aplicação em modo de desenvolvimento. Caso tenha problema de acesso, verifique se o email de teste está na lista de ***usuários de teste***
![](screenshot/gcp/18.png)
![](screenshot/gcp/19.png)
### Credenciais web
Configuração necessária para permitir que os alunos e professores acesses os recursos do Look com a conta Google.
- Observe que é necessário configurar o ***URI de redirecionamento autorizados*** para que seja possivel autenticar com sucesso com o Google. As URIs de desenvolvimento foram adicionadas, mas quando houver o dominio(www) do Look, em Produção essas configurações de URIs devem ser atualizadas.
- Salve as credencias para configurar o OAuth dos usuários Look.
![](screenshot/gcp/11.png)
![](screenshot/gcp/12.png)
![](screenshot/gcp/20.png)
### Credenciais backend
Crie uma credencial do tipo Desktop para ser usado no serviço do Look.
- Observe que o tipo de aplicativo deve ser ***App para computador***
![](screenshot/gcp/11.png)
![](screenshot/gcp/12.png)
![](screenshot/gcp/22.png)
- Salve as credencias para configurar o serviço de integração do Look.
![](screenshot/gcp/23.png)
### Look service
- Adicione as credenciais de configuração da API do Classroom
![](screenshot/gcp/24.png)
- Adicione as credenciais de configuração de OAuth dos usuários do Look
![](screenshot/gcp/25.png)
- Adicione id do projeto 
![](screenshot/gcp/26.png)
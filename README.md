# AppReceitas

## alunos do grupo
Wilson Silva Gorosthides Neto.

## Visão geral do software
O aplicativo serve como uma plataforma interativa para explorar, compartilhar e aprender sobre culinária, ele oferece uma variedade de receitas, dicas de cozinha e ferramentas para facilitar o processo de preparação de alimentos.

## Papéis, quais são os usuários e o que cada usuário pode fazer
### Usuários do Aplicativo
#### Usuário visitante:
* Podem explorar e visualizar todas as receitas disponíveis.
* Não precisam de cadastro ou login.

#### Usuários registrados:
* Devem criar uma conta e fazer login.
* Podem explorar todas as receitas.
* Podem salvar receitas favoritas.
* Podem criar e compartilhar suas próprias receitas
* Podem excluir receitas que criaram.
* Podem excluir sua própria conta.


## Requisitos funcionais, como quais os usuários que o app permite, o que cada usuário
1. Exploração de receitas (para Visitantes e Usuários registrados):
* Entrada: Acesso ao aplicativo.
* Processamento: Exibição de receitas disponíveis .
* Saída: receitas visualizadas sem interação de salvamento.

2. Cadastro de Conta :
* Entrada: Credenciais de cadastro (nome, e-mail, senha).
* Processamento: Criação de uma nova conta no banco de dados.
* Saída: Confirmação de cadastro e criação de perfil de usuario.

3. Login (para Usuários registrados):
* Entrada: Credenciais de login (e-mail, senha).
* Processamento: Verificação das credenciais e autenticação de conta.
* Saída: Acesso a funcionalidades exclusivas para usuários cadastrados.

4. Salvamento de Receitas Favoritas:
* Entrada: Seleção da receita favorita.
* Processamento: Armazenamento da receita no perfil do usuário registrado.
* Saída: Lista de receitas favoritas acessível pelo usuário.

5. Criação e Compartilhamento de Novas Receitas:
* Entrada: informações da nova receita (categoria, título, ingredientes, passos).
* Processamento: publicação de nova receita no banco de dados.
* Saída:Nova receita disponível para todos os usuários.

6. Exclusão de Receitas:
* Entrada: Solicitação de exclusão de uma receita por um usuário registrado.
* Processamento: verificação de permissões e remoção da receita do banco de dados.
* Saída: Confirmação de exclusão da receita.

7. Exclusão de Conta:
* Entrada:Solicitação de exclusão de conta por um  usuário registrado.
* Processamento: Verificação de permissões e remoção de todos os dados associados à conta do banco de dados..
* Saída: Confirmação de exclusão da conta.





## Recursos
* Fragmentos
* Menu de Opções
* List view
* Documento de String e Cores
* Banco de Dados Room.

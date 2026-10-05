# MindCare Diary Batch

O **MindCare Diary Batch** é o módulo responsável pelo processamento de tarefas agendadas e rotinas em lote do ecossistema **MindCare Diary**.

O projeto utiliza **Spring Boot** e **Spring Batch** para executar processos de backend que não precisam acontecer durante uma requisição síncrona da aplicação principal.

## Principais responsabilidades

Entre as rotinas executadas pelo batch estão:

- verificação de consultas próximas do horário agendado;
- identificação de prescrições próximas da data de vencimento;
- processamento de notificações para pacientes;
- integração com o banco de dados Oracle utilizado pelo MindCare Diary;
- execução de regras de negócio de forma agendada e desacoplada da API principal.

## Verificação de prescrições próximas do vencimento

Uma das rotinas do projeto consulta o Oracle para localizar prescrições que vencem nos próximos dias.

A regra utiliza a data de expiração da prescrição para calcular dinamicamente a quantidade de dias restantes:

```sql
TRUNC(EXPIRATION_DATE) - TRUNC(SYSDATE)
```

A consulta considera prescrições com vencimento entre a data atual e os próximos sete dias. Os dados retornados incluem informações da prescrição, do paciente e do profissional responsável, permitindo que o batch prepare e envie notificações ao paciente.

A lógica de consulta pode ser encapsulada em uma Function PL/SQL que retorna um `SYS_REFCURSOR`, consumido pela aplicação Java através de JDBC.

Exemplo de chamada JDBC:

```java
CallableStatement cs = connection.prepareCall(
    "{ ? = call MINDCARE.VERIFICA_PRESCRICOES_VENCENDO() }"
);

cs.registerOutParameter(1, Types.REF_CURSOR);
cs.execute();

ResultSet rs = (ResultSet) cs.getObject(1);
```

## Arquitetura

De forma simplificada, o fluxo funciona assim:

```text
Oracle Database
      |
      | PL/SQL / SYS_REFCURSOR
      v
Spring Batch
      |
      | processamento das prescrições e consultas
      v
Serviço de Notificação
      |
      v
Paciente
```

Essa separação permite que operações recorrentes sejam executadas de forma independente da API principal do MindCare Diary, reduzindo o acoplamento e facilitando a manutenção das rotinas agendadas.

## Tecnologias

- Java
- Spring Boot
- Spring Batch
- Spring JDBC
- JPA / Hibernate
- Oracle Database / Oracle 26ai
- PL/SQL
- Firebase Cloud Messaging
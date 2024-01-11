package com.example.bigdata;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import net.datafaker.Faker;
import net.datafaker.formats.Format;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class EsperClient {
    public static void main(String[] args) throws InterruptedException {
        int noOfRecordsPerSec;
        int howLongInSec;
        if (args.length < 2) {
            noOfRecordsPerSec = 50;
            howLongInSec = 10;
        } else {
            noOfRecordsPerSec = Integer.parseInt(args[0]);
            howLongInSec = Integer.parseInt(args[1]);
        }

        Configuration config = new Configuration();
        CompilerArguments compilerArgs = new CompilerArguments(config);

        // Compile the EPL statement
        EPCompiler compiler = EPCompilerProvider.getCompiler();
        EPCompiled epCompiled;
        try {
//            epCompiled = compiler.compile("""
//                    @public @buseventtype create json schema ScoreEvent(timestamp string, type string, status string, sender string, recipient string, amount int);
//                    @name('result') SELECT * from ScoreEvent;""", compilerArgs);
//            epCompiled = compiler.compile("""
//                    @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//                    @name('result') SELECT recipient, AVG(COALESCE(amount, 0)) AS avg, MAX(COALESCE(amount, 0)) as max FROM Transaction#time(1 min) GROUP BY recipient;""", compilerArgs);
//            epCompiled = compiler.compile("""
//                    @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//                    @name('result') SELECT * FROM Transaction WHERE amount >= 1000;""", compilerArgs);
//            epCompiled = compiler.compile("""
//                    @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//                    @name('result') SELECT recipient, amount, status FROM Transaction#time(5 sec) WHERE status = 'Rejected' GROUP BY recipient HAVING amount >= MAX(amount);""", compilerArgs);
//            epCompiled = compiler.compile("""
//            @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//            CREATE WINDOW expensesWindow#length(10) AS SELECT sender, amount FROM Transaction;
//            CREATE WINDOW incomesWindow#length(10) AS SELECT recipient, amount FROM Transaction;
//
//            ON Transaction MERGE expensesWindow INSERT SELECT sender, amount;
//            ON Transaction MERGE incomesWindow INSERT SELECT recipient, amount;
//
//            @name('result') SELECT SUM(e.amount) AS expenses, SUM(i.amount) AS incomes, e.sender AS person
//            FROM expensesWindow e
//            INNER JOIN incomesWindow i ON e.sender = i.recipient
//            GROUP BY e.sender
//            HAVING SUM(e.amount) > SUM(i.amount);
//            """, compilerArgs);
//            epCompiled = compiler.compile("""
//            @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//            @name('result') SELECT t.timestamp, t.sender, t.recipient, t_2.sender, t_2.recipient, t_3.sender, t_3.recipient
//            FROM pattern [every t=Transaction(t.sender!=t.recipient) -> t_2=Transaction(recipient=t.sender) -> t_3=Transaction(sender=t_2.sender and recipient=t.recipient)];
//            """, compilerArgs);
//            epCompiled = compiler.compile("""
//            @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//            @name('result') SELECT t_2.sender AS sender, t_2.recipient AS Recipient, t_2.amount AS Amount
//            FROM pattern [t=Transaction until t_2=Transaction(sender = recipient)]
//            WHERE t_2.amount != 0;
//            """, compilerArgs);
//            epCompiled = compiler.compile("""
//            @public @buseventtype create json schema Transaction(timestamp string, type string, status string, sender string, recipient string, amount int);
//            @name('result') SELECT t.sender AS Sender, t.recipient AS Recipient, t.amount + t_2.amount + t_3.amount AS Total_amount, t_2.status
//            FROM pattern [every t=Transaction(amount > 5000 and sender != recipient) ->
//                                t_2=Transaction(sender=t.sender and recipient=t.recipient and amount > 5000) ->
//                                t_3=Transaction(sender=t_2.sender and recipient=t_2.recipient and amount > 5000)
//                                WHERE timer:within(10) AND not Transaction(sender=t.recipient and recipient=t.sender) AND not Transaction(status="Rejected")];
//            """, compilerArgs);
//            epCompiled = compiler.compile("""
//                    @public @buseventtype create json schema Transaction(timestamp string, ets string, type string, status string, sender string, recipient string, amount int);
//                    @name('result') SELECT *
//                      FROM Transaction
//                      MATCH_RECOGNIZE (
//                      PARTITION BY sender
//                      ORDER BY timestamp
//                      MEASURES
//                      FIRST(DOWN.tstamp) AS start_ts,
//                      LAST(DOWN.tstamp) AS bottom_ts,
//                      LAST(UP.tstamp) AS end_ts
//                      ONE ROW PER MATCH
//                      AFTER MATCH SKIP PAST LAST ROW
//                      PATTERN (STRT DOWN+ UP+)
//                      DEFINE
//                      DOWN AS amount < PREV(amount),
//                      UP AS amount > PREV(amount)
//                      ) MR
//                      ORDER BY MR.sender, MR.start_ts;
//                    """, compilerArgs);
            epCompiled = compiler.compile("""
                    @public @buseventtype create json schema Transaction(timestamp string, its string, type string, status string, sender string, recipient string, amount int);
                    @name('result') SELECT * FROM Transaction;
                    """, compilerArgs);
        }
        catch (EPCompileException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }

        // Connect to the EPRuntime server and deploy the statement
        EPRuntime runtime = EPRuntimeProvider.getRuntime("http://localhost:port", config);
        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(epCompiled);
        }
        catch (EPDeployException ex) {
            // handle exception here
            throw new RuntimeException(ex);
        }

        EPStatement resultStatement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "result");

        // Add a listener to the statement to handle incoming events
        resultStatement.addListener( (newData, oldData, stmt, runTime) -> {
            for (EventBean eventBean : newData) {
                System.out.printf("R: %s%n", eventBean.getUnderlying());
            }
        });

        Faker faker = new Faker();
        TransactionStatusFaker statusFaker = new TransactionStatusFaker();
        TransactionTypeFaker typeFaker = new TransactionTypeFaker();
        TransactionPersonFaker personFaker = new TransactionPersonFaker();

        String record;

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + (1000L * howLongInSec)) {
            for (int i = 0; i < noOfRecordsPerSec; i++) {
                Timestamp timestamp = faker.date().past(30, TimeUnit.SECONDS);
                record = Format.toJson()
                        .set("timestamp", () -> timestamp.toString())
                        .set("its", () -> LocalTime.now().toString())
                        .set("type", () -> typeFaker.type().nextTypeName())
                        .set("status", () -> statusFaker.status().nextStatusName())
                        .set("sender", () -> personFaker.person().nextPersonName())
                        .set("recipient", () -> personFaker.person().nextPersonName())
                        .set("amount", () -> String.valueOf(faker.number().numberBetween(0, 10000)))
                        .build().generate();
                runtime.getEventService().sendEventJson(record, "Transaction");
            }
            waitToEpoch();
        }
    }

    static void waitToEpoch() throws InterruptedException {
        long millis = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(millis) ;
        Instant instantTrunc = instant.truncatedTo( ChronoUnit.SECONDS ) ;
        long millis2 = instantTrunc.toEpochMilli() ;
        TimeUnit.MILLISECONDS.sleep(millis2+1000-millis);
    }
}


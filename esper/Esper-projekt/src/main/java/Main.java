import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Configuration configuration = new Configuration();
//        configuration.getCommon().addEventType(KursAkcji.class);
//        EPRuntime epRuntime = EPRuntimeProvider.getDefaultRuntime(configuration);
//
//        EPDeployment deployment = compileAndDeploy(epRuntime,"""
//                select istream data, kursZamkniecia, max(kursZamkniecia)\s
//                from KursAkcji(spolka = 'Oracle')#ext_timed(data.getTime(), 7 days);""");
//
//        ProstyListener prostyListener = new ProstyListener();
//
//        for (EPStatement statement : deployment.getStatements()) {
//            statement.addListener(prostyListener);
//        }
//
//        InputStream inputStream = new InputStream();
//        inputStream.generuj(epRuntime.getEventService());

        TransactionTypeFaker faker = new TransactionTypeFaker();
        System.out.println(faker.type().nextTypeName());
        TransactionStatusFaker status_faker = new TransactionStatusFaker();
        System.out.println(status_faker.status().nextStatusName());


    }

    public static EPDeployment compileAndDeploy(EPRuntime epRuntime, String epl) {
        EPDeploymentService deploymentService = epRuntime.getDeploymentService();
        CompilerArguments args =
                new CompilerArguments(epRuntime.getConfigurationDeepCopy());
        EPDeployment deployment;
        try {
            EPCompiled epCompiled = EPCompilerProvider.getCompiler().compile(epl, args);
            deployment = deploymentService.deploy(epCompiled);
        } catch (EPCompileException e) {
            throw new RuntimeException(e);
        } catch (EPDeployException e) {
            throw new RuntimeException(e);
        }
        return deployment;
    }

}

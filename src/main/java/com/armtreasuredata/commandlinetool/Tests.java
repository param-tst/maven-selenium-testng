package com.armtreasuredata.commandlinetool;

import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.treasuredata.client.ExponentialBackOff;
import com.treasuredata.client.TDClient;
import com.treasuredata.client.TDClientHttpNotFoundException;
import com.treasuredata.client.model.TDDatabase;
import com.treasuredata.client.model.TDJob;
import com.treasuredata.client.model.TDJobRequest;
import com.treasuredata.client.model.TDJobSummary;
import com.treasuredata.client.model.TDResultFormat;
import com.treasuredata.client.model.TDTable;
import picocli.CommandLine;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class Tests

{

	private static boolean tableExist = false;
	private static boolean dbExist = false;

	@Test
	@Parameters({ "qeryInputs" })
	public static void test(String arg)

	{
		String[] args = arg.split(" ");
		String jobId = null;
		TDClient client = TDClient.newClient();
		QueryConfiguration2 dbConfig = new QueryConfiguration2();
		System.out.println(args.length);
		new CommandLine(dbConfig).parseArgs(args);

		try {

			// Retrieve database and table names

			List<TDDatabase> databases = client.listDatabases();
			for (TDDatabase db1 : client.listDatabases()) {
				System.out.println(" DB: " + db1.getName());
				if (db1.getName().equals(dbConfig.getDb_name())) {
					dbExist = true;
					break;
				}
			}
			System.out.println(" Table: " + client.listTables(dbConfig.getDb_name()).size());
			// can be replaced with throw statement and later can be handled in catch to
			// inform user
//			if (!dbExist)
//				return;

			for (TDTable table : client.listTables(dbConfig.getDb_name())) {
				System.out.println(" Table: " + table);
				if (table.getName().equals(dbConfig.getTable_name())) {
					tableExist = true;
					break;
				}

			}
			// can be replaced with throw statement and later can be handled in catch to
			// inform user
//			if (!tableExist)
//				return;
			// Submit a new Presto query
			if (dbConfig.getEngine().contains("presto"))
				jobId = client.submit(TDJobRequest.newPrestoQuery(dbConfig.getDb_name(), dbConfig.toString()));
			else
				jobId = client.submit(TDJobRequest.newHiveQuery(dbConfig.getDb_name(), dbConfig.toString()));

			// Wait until the query finishes

			ExponentialBackOff backOff = new ExponentialBackOff();

			TDJobSummary job = client.jobStatus(jobId);

			while (!job.getStatus().isFinished()) {

				Thread.sleep(backOff.nextWaitTimeMillis());

				job = client.jobStatus(jobId);

			}

			// Read the detailed job information

			TDJob jobInfo = client.jobInfo(jobId);

			System.out.println("log:\n" + jobInfo.getCmdOut());

			System.out.println("error log:\n" + jobInfo.getStdErr());

			// Read the job results in msgpack.gz format

			client.jobResult(jobId, TDResultFormat.MESSAGE_PACK_GZ, new Function<InputStream, Integer>()

			{

				@Override
				public Integer apply(InputStream input) {
					int count = 0;

					try {

						MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(new GZIPInputStream(input));

						while (unpacker.hasNext()) {

							// Each row of the query result is array type value (e.g., [1, "name", ...])

							ArrayValue array = unpacker.unpackValue().asArrayValue();

							System.out.println(array);

							count++;

						}

						unpacker.close();

					}

					catch (Exception e) {

						throw Throwables.propagate(e);

					}

					return count;

				}

			});

		} catch (InvalidCommandLineException clException) {
			clException.printStackTrace();

		} catch (InvalidOptionConfigurationException configException) {
			configException.printStackTrace();

		} catch (UnrecognizedSwitchException unrecognizedSwitchException) {
			unrecognizedSwitchException.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

	}

}
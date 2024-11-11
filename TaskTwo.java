// Enums for Mode and Database Identifiers
enum ModeIdentifier { DUMP, PASSTHROUGH, VALIDATE }
enum DatabaseIdentifier { POSTGRES, REDIS, ELASTIC }

// DataPoint class
class DataPoint {
    // Fields and methods representing data (raw or processed)
}

// ProcessorMode Interface
interface ProcessorMode {
    void process(DataPoint data, Database database);
}

// DumpMode: Simply discards the data
class DumpMode implements ProcessorMode {
    @Override
    public void process(DataPoint data, Database database) {
        // Discard data (no action needed)
        System.out.println("Data discarded in DumpMode.");
    }
}

// PassthroughMode: Inserts data into the configured database
class PassthroughMode implements ProcessorMode {
    @Override
    public void process(DataPoint data, Database database) {
        database.insert(data);
    }
}

// ValidateMode: Validates then inserts data into the configured database
class ValidateMode implements ProcessorMode {
    @Override
    public void process(DataPoint data, Database database) {
        if (database.validate(data)) {
            database.insert(data);
        } else {
            System.out.println("Data validation failed in ValidateMode.");
        }
    }
}

// Database Interface
interface Database {
    void connect();
    void insert(DataPoint data);
    boolean validate(DataPoint data);
}

// PostgresDatabase: Concrete implementation of Database
class PostgresDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to Postgres database.");
    }

    @Override
    public void insert(DataPoint data) {
        System.out.println("Data inserted into Postgres database.");
    }

    @Override
    public boolean validate(DataPoint data) {
        System.out.println("Data validated in Postgres database.");
        return true; // Assuming validation is successful for simplicity
    }
}

// RedisDatabase: Concrete implementation of Database
class RedisDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to Redis database.");
    }

    @Override
    public void insert(DataPoint data) {
        System.out.println("Data inserted into Redis database.");
    }

    @Override
    public boolean validate(DataPoint data) {
        System.out.println("Data validated in Redis database.");
        return true;
    }
}

// ElasticDatabase: Concrete implementation of Database
class ElasticDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to Elastic database.");
    }

    @Override
    public void insert(DataPoint data) {
        System.out.println("Data inserted into Elastic database.");
    }

    @Override
    public boolean validate(DataPoint data) {
        System.out.println("Data validated in Elastic database.");
        return true;
    }
}

// DataProcessor Class
class DataProcessor {
    private ProcessorMode currentMode;
    private Database currentDatabase;

    // Configure the mode and database
    public void configure(ModeIdentifier mode, DatabaseIdentifier db) {
        currentMode = switch (mode) {
            case DUMP -> new DumpMode();
            case PASSTHROUGH -> new PassthroughMode();
            case VALIDATE -> new ValidateMode();
        };

        currentDatabase = switch (db) {
            case POSTGRES -> new PostgresDatabase();
            case REDIS -> new RedisDatabase();
            case ELASTIC -> new ElasticDatabase();
        };

        // Connect to the chosen database
        if (currentDatabase != null) {
            currentDatabase.connect();
        }
    }

    // Process data according to the current mode and database
    public void process(DataPoint data) {
        if (currentMode != null && currentDatabase != null) {
            currentMode.process(data, currentDatabase);
        } else {
            System.out.println("Processor is not configured. Please configure the mode and database.");
        }
    }
}

// Example Usage
public class TaskTwo {
    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor();
        
        // Configure processor to Passthrough mode and Postgres database
        processor.configure(ModeIdentifier.PASSTHROUGH, DatabaseIdentifier.POSTGRES);
        processor.process(new DataPoint());

        // Reconfigure processor to Validate mode and Redis database
        processor.configure(ModeIdentifier.VALIDATE, DatabaseIdentifier.REDIS);
        processor.process(new DataPoint());
    }
}


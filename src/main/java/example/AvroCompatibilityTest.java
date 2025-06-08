package example;

import org.apache.avro.Schema;
import org.apache.avro.generic.*;
import org.apache.avro.io.*;

import java.io.*;

public class AvroCompatibilityTest {

    public static void main(String[] args) throws Exception {
        test("schemas/User_v1.avsc", "schemas/User_v2.avsc");
        test("schemas/User_v2.avsc", "schemas/User_v1.avsc");
        test("schemas/User_v1.avsc", "schemas/User_v3.avsc");
    }

    private static void test(String writerPath, String readerPath) throws Exception {
        Schema writerSchema = new Schema.Parser().parse(new File(writerPath));
        Schema readerSchema = new Schema.Parser().parse(new File(readerPath));

        GenericRecord record = new GenericData.Record(writerSchema);
        record.put("name", "Alice");
        if (writerSchema.getField("age") != null) {
            record.put("age", 42);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(writerSchema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(record, encoder);
        encoder.flush();

        byte[] data = out.toByteArray();

        try {
            DatumReader<GenericRecord> reader = new GenericDatumReader<>(writerSchema, readerSchema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            GenericRecord result = reader.read(null, decoder);
            System.out.printf("✅ Read successful (Writer: %s → Reader: %s)%n", writerPath, readerPath);
            System.out.println("   ➤ Result: " + result);
        } catch (Exception e) {
            System.out.printf("❌ Read failed (Writer: %s → Reader: %s)%n", writerPath, readerPath);
            e.printStackTrace(System.out);
        }
    }
}

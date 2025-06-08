# Avro Schema Compatibility Demo

This project demonstrates how Apache Avro handles schema evolution between different versions of the same schema.

## 🔍 Schemas

- `User_v1.avsc`: Basic schema with a `name` field.
- `User_v2.avsc`: Adds an `age` field with a default (backward-compatible).
- `User_v3.avsc`: Renames the `name` field to `fullName` (incompatible).

## 🧪 Compatibility Tests

The program serializes a record with one schema version, then attempts to read it with another.

### Test Cases:

- ✅ `v1 → v2`: Forward compatibility
- ✅ `v2 → v1`: Backward compatibility
- ❌ `v1 → v3`: Incompatible change

## 🚀 Run

```bash
mvn compile exec:java -Dexec.mainClass="example.AvroCompatibilityTest"
```

Or compile & run:

```bash
mvn compile
java -cp target/classes:~/.m2/repository/org/apache/avro/avro/1.11.1/avro-1.11.1.jar example.AvroCompatibilityTest
```

## 🧠 Tip

Please have a look at my tutorial: TODO: Insert title & url

---

**2025 Micha Kops / hasCode.com**

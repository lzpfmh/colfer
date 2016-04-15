package testdata;

// This file was generated by colf(1); DO NOT EDIT

/**
 * @author Commander Colfer
 * @see <a href="https://github.com/pascaldekloe/colfer">Colfer's home</a>
 */
public class O implements java.io.Serializable {

	private static final java.nio.charset.Charset utf8 = java.nio.charset.Charset.forName("UTF-8");

	public boolean b;
	public int u32;
	public long u64;
	public int i32;
	public long i64;
	public float f32;
	public double f64;
	public java.time.Instant t;
	public String s;
	public byte[] a;


	/**
	 * Writes in Colfer format.
	 * @param buf the data destination.
	 * @throws java.nio.BufferOverflowException when {@code buf} is too small.
	 */
	public final void marshal(java.nio.ByteBuffer buf) {
		buf.order(java.nio.ByteOrder.BIG_ENDIAN);
		buf.put((byte) 0x80);

		if (this.b) {
			buf.put((byte) 0);
		}

		if (this.u32 != 0) {
			buf.put((byte) 1);
			putVarint(buf, this.u32);
		}

		if (this.u64 != 0) {
			buf.put((byte) 2);
			putVarint(buf, this.u64);
		}

		if (this.i32 != 0) {
			byte header = 3;
			int x = this.i32;
			if (x < 0) {
				x = -x;
				header |= 0x80;
			}
			buf.put(header);
			putVarint(buf, x);
		}

		if (this.i64 != 0) {
			byte header = 4;
			long x = this.i64;
			if (x < 0) {
				x = -x;
				header |= 0x80;
			}
			buf.put(header);
			putVarint(buf, x);
		}

		if (this.f32 != 0.0f) {
			buf.put((byte) 5);
			buf.putFloat(this.f32);
		}

		if (this.f64 != 0.0) {
			buf.put((byte) 6);
			buf.putDouble(this.f64);
		}

		if (this.t != null) {
			long s = this.t.getEpochSecond();
			int ns = this.t.getNano();
			if (! (s == 0 && ns == 0)) {
				byte header = 7;
				if (ns != 0) header |= 0x80;
				buf.put(header);
				buf.putLong(s);
				if (ns != 0) buf.putInt(ns);
			}
		}

		if (this.s != null && ! this.s.isEmpty()) {
			java.nio.ByteBuffer bytes = utf8.encode(this.s);
			buf.put((byte) 0x08);
			putVarint(buf, bytes.limit());
			buf.put(bytes);
		}

		if (this.a != null && this.a.length != 0) {
			buf.put((byte) 0x09);
			putVarint(buf, this.a.length);
			buf.put(this.a);
		}

	}

	/**
	 * Reads in Colfer format.
	 * @param buf the data source.
	 * @throws java.nio.BufferUnderflowException when {@code buf} is incomplete.
	 */
	public final void unmarshal(java.nio.ByteBuffer buf) {
		int header = buf.get() & 0xff;
		if (header != 0x80)
			throw new IllegalArgumentException("magic header mismatch");

		if (! buf.hasRemaining()) return;
		header = buf.get() & 0xff;
		int field = header & 0x7f;

		if (field == 0) {
			this.b = true;

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 1) {
			this.u32 = getVarint32(buf);

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 2) {
			this.u64 = getVarint64(buf);

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 3) {
			this.i32 = getVarint32(buf);
			if ((header & 0x80) != 0)
			this.i32 = (~this.i32) + 1;

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 4) {
			this.i64 = getVarint64(buf);
			if ((header & 0x80) != 0)
			this.i64 = (~this.i64) + 1;

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 5) {
			this.f32 = buf.getFloat();

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 6) {
			this.f64 = buf.getDouble();

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 7) {
			long s = buf.getLong();
			if ((header & 0x80) == 0) {
				this.t = java.time.Instant.ofEpochSecond(s);
			} else {
				int ns = buf.getInt();
				this.t = java.time.Instant.ofEpochSecond(s, ns);
			}

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 8) {
			int length = getVarint32(buf);
			java.nio.ByteBuffer blob = java.nio.ByteBuffer.allocate(length);
			buf.get(blob.array());
			this.s = utf8.decode(blob).toString();

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		if (field == 9) {
			int length = getVarint32(buf);
			this.a = new byte[length];
			buf.get(a);

			if (! buf.hasRemaining()) return;
			header = buf.get() & 0xff;
			field = header & 0x7f;
		}

		throw new IllegalArgumentException("pending data");
	}

	/**
	 * Serializes an integer.
	 * @param buf the data destination.
	 * @param x the value.
	 */
	private static void putVarint(java.nio.ByteBuffer buf, int x) {
		while ((x & 0xffffff80) != 0) {
			buf.put((byte) (x | 0x80));
			x >>>= 7;
		}
		buf.put((byte) x);
	}

	/**
	 * Serializes an integer.
	 * @param buf the data destination.
	 * @param x the value.
	 */
	private static void putVarint(java.nio.ByteBuffer buf, long x) {
		while ((x & 0xffffffffffffff80L) != 0) {
			buf.put((byte) (x | 0x80));
			x >>>= 7;
		}
		buf.put((byte) x);
	}

	/**
	 * Deserializes a 32-bit integer.
	 * @param buf the data source.
	 * @return the value.
	 */
	private static int getVarint32(java.nio.ByteBuffer buf) {
		int x = 0;
		int shift = 0;
		while (true) {
			int b = buf.get() & 0xff;
			x |= (b & 0x7f) << shift;
			if (b < 0x80) return x;
			shift += 7;
		}
	}

	/**
	 * Deserializes a 64-bit integer.
	 * @param buf the data source.
	 * @return the value.
	 */
	private static long getVarint64(java.nio.ByteBuffer buf) {
		long x = 0;
		int shift = 0;
		while (true) {
			long b = buf.get() & 0xffL;
			x |= (b & 0x7f) << shift;
			if (b < 0x80) return x;
			shift += 7;
		}
	}

}

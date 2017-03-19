package mosing.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PendaftarModel {

	private String username;
	private String no_id;
	private String nama_id;
	private String nama_ijazah;
	private String foto;
	private String no_hp;
	private String no_telp;
	private String negara;
	private String kewarganegaraan;
	private String alamat_tetap;
	private String jenis_id;
	private String alamat_sekarang;
	private Date tgl_lahir;
	private String provinsi;
	private String kota;
	private byte jenis_kelamin;
	private String no_daftar;
}

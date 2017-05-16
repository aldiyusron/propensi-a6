package mosing.service;

import java.util.List;

import mosing.model.PengawasUjianModel;

public interface PengawasUjianService {

	void addPengawas(PengawasUjianModel pengawas);
	PengawasUjianModel selectPengawas(String username);
	List<PengawasUjianModel> selectAllPengawas();
	void terimaPengawas(PengawasUjianModel pengawas);
	void tolakPengawas(PengawasUjianModel pengawas);
	void updatePengawas(PengawasUjianModel pengawas);
	List<PengawasUjianModel> selectPengawasUjianTerseleksi(int id_lokasi);
	List<PengawasUjianModel> selectPengawasUjianLokasi(int id_lokasi);
}

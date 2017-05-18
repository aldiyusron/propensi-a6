package mosing.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mosing.model.FakultasModel;
import mosing.model.JalurMasukModel;
import mosing.model.ListStrings;
import mosing.model.NilaiModel;
import mosing.model.NilaiUjianModel;
import mosing.model.PendaftarModel;
import mosing.model.PenyeleksianModel;
import mosing.model.ProdiTersediaModel;
import mosing.service.CalonMahasiswaService;
import mosing.service.FakultasService;
import mosing.service.JalurMasukService;
import mosing.service.NilaiService;
import mosing.service.PFakultasService;
import mosing.service.PendaftarService;
import mosing.service.PenyeleksianService;
import mosing.service.ProdiService;

@Controller
public class PUnivController {
	@Autowired
	PenyeleksianService penyeleksianDAO;

	@Autowired
	FakultasService fakultasDAO;

	@Autowired
	JalurMasukService jalurMasukDAO;

	@Autowired
	PFakultasService pfakultasDAO;

	@Autowired
	ProdiService prodiDAO;

	@Autowired
	PendaftarService pendaftarDAO;

	@Autowired
	NilaiService nilaiDAO;

	@Autowired
	CalonMahasiswaService calonDAO;

	// nampilin jalur masuk
	@RequestMapping("/seleksi-pendaftar")
	public String lihatDaftarJalurMasuk(Model model) {
		List<JalurMasukModel> allJalur = jalurMasukDAO.selectAllJalurMasuk();
		model.addAttribute("allJalur", allJalur);

		return "pilih-jalur-seleksi";
	}

	// nampilin list fakultas
	@RequestMapping("/lihat/fakultas/{id_jalur}")
	public String lihatFakultas(Model model, @PathVariable(value = "id_jalur") int id_jalur) {
		List<FakultasModel> fakultas = pfakultasDAO.selectAllFakultas();
		for (int i = 0; i < fakultas.size(); i++) {
			int id_fakultas = fakultas.get(i).getId_fakultas();
			List<ProdiTersediaModel> prodi = prodiDAO.selectAllProdiFak(id_fakultas);
			List<ProdiTersediaModel> prodiJalur = new ArrayList<ProdiTersediaModel>();
			for (int j = 0; j < prodi.size(); j++) {
				if (prodi.get(j).getId_jalur() == id_jalur) {
					prodiJalur.add(prodi.get(j));
				}
			}
			fakultas.get(i).setListProdi(prodiJalur);
		}
		model.addAttribute("id_jalur", id_jalur);
		model.addAttribute("fakultas", fakultas);
		return "view-fakultas-seleksi";
	}

	// nampilin list pendaftar ppkb di prodi dan fakultas tertentu
	@RequestMapping("/lihat/pendaftar/{id_jalur}/{id_prodi}")
	public String listPendaftar(Model model, @PathVariable(value = "id_jalur") int id_jalur,
			@PathVariable(value = "id_prodi") int id_prodi) {
		List<PendaftarModel> pendaftar = pendaftarDAO.selectAllPendaftarSemua(id_prodi, id_jalur);
		JalurMasukModel jalur = jalurMasukDAO.selectJalurMasuk(id_jalur);

		List<PenyeleksianModel> penyeleksian = new ArrayList<PenyeleksianModel>();
		if (jalur.getJenis_jalur() == 0 & jalur.getNama_jenjang().equalsIgnoreCase("S1")) {
			for (int i = 0; i < pendaftar.size(); i++) {
				int no_daftar = pendaftar.get(i).getNo_daftar();
				List<NilaiModel> nilai = nilaiDAO.selectNilai(no_daftar);
				int nilaiMTK = 0;
				int nilaiBIND = 0;
				int nilaiBING = 0;
				int nilaiKIM = 0;
				int nilaiFIS = 0;
				int nilaiBIO = 0;
				int nilaiSEJ = 0;
				int nilaiGEO = 0;
				int nilaiEKO = 0;
				for (int j = 0; j < nilai.size(); j++) {
					nilaiMTK = nilaiMTK + nilai.get(j).getMtk();
					nilaiBIND = nilaiBIND + nilai.get(j).getBindo();
					nilaiBING = nilaiBING + nilai.get(j).getBing();
					nilaiKIM = nilaiKIM + nilai.get(j).getKimia();
					nilaiFIS = nilaiFIS + nilai.get(j).getFisika();
					nilaiBIO = nilaiBIO + nilai.get(j).getBiologi();
					nilaiSEJ = nilaiSEJ + nilai.get(j).getSejarah();
					nilaiGEO = nilaiGEO + nilai.get(j).getGeografi();
					nilaiEKO = nilaiEKO + nilai.get(j).getEkonomi();
				}
				double rata2 = ((nilaiMTK / 5) + (nilaiBIND / 5) + (nilaiBING / 5) + (nilaiKIM / 5) + (nilaiFIS / 5)
						+ (nilaiBIO / 5) + (nilaiSEJ / 5) + (nilaiGEO / 5) + (nilaiEKO / 5)) / 6;
				pendaftar.get(i).setRata2(rata2);
				PenyeleksianModel seleksi = penyeleksianDAO.selectPenyeleksian2(no_daftar);
				penyeleksian.add(seleksi);
				model.addAttribute("nilai", nilai);
			}
		}

		else if (jalur.getJenis_jalur() == 1 & jalur.getNama_jenjang().equalsIgnoreCase("S1")) {
			for (int i = 0; i < pendaftar.size(); i++) {
				int no_daftar = pendaftar.get(i).getNo_daftar();
				NilaiUjianModel nilai = nilaiDAO.selectNilaiUjian(no_daftar);
				double rata2Umum = (nilai.getBindo() + nilai.getBing() + nilai.getMtk_dasar() + nilai.getTpa()) / 4;
				double rata2IPA = (nilai.getBiologi() + nilai.getFisika() + nilai.getKimia() + nilai.getMtk()) / 4;
				double rata2IPS = (nilai.getEkonomi() + nilai.getSejarah() + nilai.getGeografi() + nilai.getSosiologi())
						/ 4;
				double rata2 = 0;
				if (rata2IPA == 0) {
					rata2 = (rata2Umum + rata2IPS) / 2;
				} else if (rata2IPS == 0) {
					rata2 = (rata2Umum + rata2IPA) / 2;
				} else {
					rata2 = (rata2Umum + rata2IPA + rata2IPS) / 3;
				}
				pendaftar.get(i).setRata2(rata2);
				PenyeleksianModel seleksi = penyeleksianDAO.selectPenyeleksian2(no_daftar);
				penyeleksian.add(seleksi);
				model.addAttribute("nilai", nilai);
			}
		}

		else {
			for (int i = 0; i < pendaftar.size(); i++) {
				int no_daftar = pendaftar.get(i).getNo_daftar();
				// NilaiUjianModel nilai = nilaiDAO.selectNilaiUjian(no_daftar);
				// double rata2Umum = (nilai.getBing() + nilai.getTpa()) / 2;
				// double rata2 = rata2Umum;
				// pendaftar.get(i).setRata2(rata2);
				PenyeleksianModel seleksi = penyeleksianDAO.selectPenyeleksian2(no_daftar);
				penyeleksian.add(seleksi);
				// model.addAttribute("nilai", nilai);
			}
		}
		ProdiTersediaModel prodi = prodiDAO.selectProdi(id_prodi);
		model.addAttribute("prodi", prodi);
		model.addAttribute("jalur", jalur);
		model.addAttribute("penyeleksian", penyeleksian);
		model.addAttribute("statusSubmit", new ListStrings());
		model.addAttribute("pendaftar", pendaftar);
		return "view-all-pendaftar";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@RequestMapping(value = "/sukses-seleksi")
	public String suksesSeleksi(@ModelAttribute(value = "statusSubmit") @Valid ListStrings statusSubmit,
			BindingResult bindingResultStatus, Model model) {
		System.out.println("size:" + statusSubmit.getStrings().size());
		System.out.println("binding result:" + bindingResultStatus.getModel().toString());
		System.out.println(statusSubmit.getStrings().get(0));
		for (int i = 0; i < statusSubmit.getStrings().size(); i++) {
			if (statusSubmit.getStrings().get(i) != null) {
				String nomor = statusSubmit.getStrings().get(i);
				int no_daftar = Integer.parseInt(nomor);
				penyeleksianDAO.updateLulus(no_daftar);
			}
		}
		return "success-daftarpengawas"; //belum ganti bouz
	}
}

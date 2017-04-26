package mosing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mosing.mapper.PendaftarMapper;
import mosing.model.DaftarPilihanModel;
import mosing.model.PendaftarModel;
import mosing.model.PenyeleksianModel;

@Slf4j
@Service
public class PendaftarServiceDatabase implements PendaftarService {
	
	@Autowired
	PendaftarMapper pendaftarMapper;
	
	@Override
	public PendaftarModel selectPendaftar2 (String username)
	{
		return pendaftarMapper.selectPendaftar2(username);
	}
	
	@Override
	public PendaftarModel selectPendaftar (String no_id)
	{
		return pendaftarMapper.selectPendaftar(no_id);
	}
	
	@Override
	public void addPendaftar (PendaftarModel pendaftar)
	{
		pendaftarMapper.addPendaftar (pendaftar);
	}

	@Override
	public List<PendaftarModel> selectAllPendaftarTerverifikasi() {
		// TODO Auto-generated method stub
		return pendaftarMapper.selectAllPendaftarTerverifikasi();
	}

	@Override
	public List<PendaftarModel> selectAllPendaftarTakTerverifikasi() {
		// TODO Auto-generated method stub
		return pendaftarMapper.selectAllPendaftarTakTerverifikasi();
	}
	
	public List<PendaftarModel> selectAllPendaftar() {
		// TODO Auto-generated method stub
		return pendaftarMapper.selectAllPendaftar();
	}

	@Override
	public void updateDataPendaftar(PendaftarModel pendaftar) {
		// TODO Auto-generated method stub
		pendaftarMapper.updateDataPendaftar(pendaftar);
	}
	
	@Override
	public void addDaftarPilihan (DaftarPilihanModel daftar)
	{
		pendaftarMapper.addDaftarPilihan(daftar);
	}
	
	@Override
	public void updateFoto(String foto, int id_user)
	{
		pendaftarMapper.updateFoto(foto, id_user);
	}
}

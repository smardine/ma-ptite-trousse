package fr.smardine.matroussedemaquillage.ftp;

import java.io.IOException;
import java.util.Locale;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FileTransferClient;
import com.enterprisedt.net.ftp.WriteMode;

/**
 * @author smardine
 */
public class connexionFTP {
	FileTransferClient ftp;

	/**
	 * @param host
	 * @param username
	 * @param password
	 * @throws FTPException
	 */
	public connexionFTP(String host, String username, String password)
			throws FTPException {
		ftp = new FileTransferClient();

		ftp.setRemoteHost(host);
		ftp.setUserName(username);
		ftp.setPassword(password);
		ChangeLocalToFrench();

	}

	/**
	 * @return true ou false
	 */
	public boolean connexion() {

		try {
			ftp.connect();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IO exeption : " + e);
			return false;
		}
		return true;

	}

	/**
	 * @return true ou false
	 */
	public boolean deconnexion() {
		try {
			ftp.disconnect();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("deconnexion FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("deconnexion IO exeption : " + e);
			return false;
		}
		return true;

	}

	/**
	 * 
	 */
	public void ChangeLocalToFrench() {
		Locale[] locales = new Locale[2];
		locales[0] = Locale.FRENCH;
		locales[1] = Locale.getDefault();
		ftp.getAdvancedFTPSettings().setParserLocales(locales);

	}

	/**
	 * @return true ou false
	 */
	public boolean ASCIIMODE() {
		try {
			ftp.setContentType(FTPTransferType.ASCII);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ASCIIMODE FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ASCIIMODE IO exeption : " + e);
			return false;
		}
		return true;
	}

	/**
	 * @return true ou false
	 */
	public boolean BINARYMODE() {

		try {
			ftp.setContentType(FTPTransferType.BINARY);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("BINARYMODE FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("BINARYMODE IO exeption : " + e);
			return false;
		}
		return true;
	}

	/**
	 * @param active
	 * @param passive
	 * @return true ou false
	 */
	public boolean ActiveOrPassiveMod(boolean active, boolean passive) {
		if (active == true && passive == false) {
			try {
				ftp.getAdvancedFTPSettings().setConnectMode(
						FTPConnectMode.ACTIVE);
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				System.out.println("ActiveOrPassiveMod FTP exeption : " + e);
				return false;
			}
		}
		if (active == false && passive == true) {
			try {
				ftp.getAdvancedFTPSettings()
						.setConnectMode(FTPConnectMode.PASV);
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				System.out.println("ActiveOrPassiveMod FTP exeption : " + e);
				return false;
			}
		}
		if ((active == true && passive == true)
				|| (active == false && passive == false)) {
			// on ne peut pas avoir a la fois un mod active et passive
			// et il faut qu'aun moins un des mod soit actif
			return false;
		}

		return true;
	}

	/**
	 * @param DirName
	 * @return tab de sous dossiers
	 */
	public String[] getParticularDirectory(String DirName) {

		String[] descriptions = null;
		try {
			descriptions = ftp.directoryNameList(DirName, true);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getParticularDirectory FTP exeption : " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getParticularDirectory IO exeption : " + e);
		}
		return descriptions;

	}

	/**
	 * @return chemin du repertoire
	 */
	public String getDirectory() {
		String directory = null;
		try {
			directory = ftp.getRemoteDirectory();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getDirectory FTP exeption : " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getDirectory IO exeption : " + e);
		}
		return directory;
	}

	/**
	 * @return tab de sous dossier
	 */
	public String[] getListDirectory() {
		String[] files = null;
		try {
			files = ftp.directoryNameList();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getListDirectory FTP exeption : " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("getListDirectory IO exeption : " + e);
		}
		return files;
	}

	/**
	 * @param TargetDirectory
	 * @return true ou false
	 */
	public boolean ChangeDirectory(String TargetDirectory) {
		// on attrape la "directory actuelle"
		String directory = null;
		try {
			directory = ftp.getRemoteDirectory();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory IO exeption : " + e);
			return false;
		}
		// on change la "directory"
		try {
			ftp.changeDirectory(TargetDirectory);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory IO exeption : " + e);
			return false;
		}
		// on verifie que la directory a bien changé
		try {
			directory = ftp.getRemoteDirectory();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeDirectory IO exeption : " + e);
			return false;
		}
		if (directory.equals(TargetDirectory)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return true ou false
	 */
	public boolean ChangeToParentDirectory() {
		try {
			ftp.changeToParentDirectory();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeToParentDirectory FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("ChangeToParentDirectory IO exeption : " + e);
			return false;
		}

		return true;
	}

	/**
	 * @param LocalFilePath
	 * @param RemoteFilePath
	 * @return true ou false
	 */
	public boolean UploadFile(String LocalFilePath, String RemoteFilePath) {
		try {
			ftp.uploadFile(LocalFilePath, RemoteFilePath);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("UploadFile FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("UploadFile IO exeption : " + e);
			return false;
		}

		return true;
	}

	/**
	 * @param LocalFilePath
	 * @param RemoteFilePath
	 * @return true ou false
	 */
	public boolean UploadWithAppend(String LocalFilePath, String RemoteFilePath) {
		try {
			ftp.uploadFile(LocalFilePath, RemoteFilePath, WriteMode.APPEND);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("UploadWithAppend FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("UploadWithAppend IO exeption : " + e);
			return false;
		}

		return true;
	}

	/**
	 * @param localFilePath
	 * @param remoteFileName
	 * @return true ou false
	 */
	public boolean DownloadFile(String localFilePath, String remoteFileName) {
		try {
			ftp.downloadFile(localFilePath, remoteFileName);
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DownloadFile FTP exeption : " + e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DownloadFile IO exeption : " + e);
			return false;
		}
		return true;
	}

}

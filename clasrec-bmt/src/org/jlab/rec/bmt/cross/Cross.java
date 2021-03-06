package org.jlab.rec.bmt.cross;

import java.util.ArrayList;

import org.jlab.geom.prim.Point3D;
import org.jlab.geom.prim.Vector3D;
import org.jlab.rec.bmt.Constants;

import org.jlab.rec.bmt.cluster.Cluster;
import org.jlab.rec.bmt.hit.FittedHit;

/**
 * The crosses are objects used to find tracks and are characterized by a 3-D point and a direction unit vector.
 * @author ziegler
 *
 */
public class Cross extends ArrayList<Cluster> implements Comparable<Cross> {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 5317526429163382618L;

	/**
	 * 
	 * @param sector the sector (1)
	 * @param region the region (1...3)
	 * @param rid the cross ID (if there are only 3 crosses in the event, the ID corresponds to the region index
	 */
	public Cross(int sector, int region, int rid) {
		this._Sector = sector;
		this._Region = region;
		this._Id = rid;
	}
	
	private int _Sector;      							//	    sector[1...6]
	private int _Region;    		 					//	    region [1,...3]
	private int _Id;									//		cross Id

	// point parameters:
	private Point3D _Point;
	private Point3D _PointErr;

	private Vector3D _Dir;
	private Vector3D _DirErr;
	
	/**
	 * 
	 * @return the sector of the cross
	 */
	public int get_Sector() {
		return _Sector;
	}

	/**
	 * Sets the sector
	 * @param _Sector  the sector of the cross
	 */
	public void set_Sector(int _Sector) {
		this._Sector = _Sector;
	}

	/**
	 * 
	 * @return the region of the cross
	 */
	public int get_Region() {
		return _Region;
	}

	/**
	 * Sets the region
	 * @param _Region  the region of the cross
	 */
	public void set_Region(int _Region) {
		this._Region = _Region;
	}

	/**
	 * 
	 * @return the id of the cross
	 */
	public int get_Id() {
		return _Id;
	}

	/**
	 * Sets the cross ID
	 * @param _Id  the id of the cross
	 */
	public void set_Id(int _Id) {
		this._Id = _Id;
	}

	
	

	/**
	 * 
	 * @return a 3-D point characterizing the position of the cross in the tilted coordinate system.
	 */
	public Point3D get_Point() {
		return _Point;
	}

	/**
	 * Sets the cross 3-D point 
	 * @param _Point  a 3-D point characterizing the position of the cross in the tilted coordinate system.
	 */
	public void set_Point(Point3D _Point) {
		this._Point = _Point;
	}

	/**
	 * 
	 * @return a 3-dimensional error on the 3-D point characterizing the position of the cross in the tilted coordinate system.
	 */
	public Point3D get_PointErr() {
		return _PointErr;
	}

	/**
	 * Sets a 3-dimensional error on the 3-D point 
	 * @param _PointErr a 3-dimensional error on the 3-D point characterizing the position of the cross in the tilted coordinate system.
	 */
	public void set_PointErr(Point3D _PointErr) {
		this._PointErr = _PointErr;
	}

	/**
	 * 
	 * @return the cross unit direction vector
	 */
	public Vector3D get_Dir() {
		return _Dir;
	}

	/**
	 * Sets the cross unit direction vector
	 * @param _Dir the cross unit direction vector
	 */
	public void set_Dir(Vector3D _Dir) {
		this._Dir = _Dir;
	}

	/**
	 * 
	 * @return the cross unit direction vector
	 */
	public Vector3D get_DirErr() {
		return _DirErr;
	}

	/**
	 * Sets the cross unit direction vector
	 * @param _DirErr the cross unit direction vector
	 */
	public void set_DirErr(Vector3D _DirErr) {
		this._DirErr = _DirErr;
	}

	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Sorts crosses by azimuth angle values
	 */
	@Override
	public int compareTo(Cross arg) {
		
		if(this.get_Point().toVector3D().phi()<arg.get_Point().toVector3D().phi()) {
			return 1;
		} else {
			return -1;
		}
	}

	private Cluster _clus1;
	private Cluster _clus2;
	
	/**
	 * Set the first cluster (corresponding to the first superlayer in a region)
	 * @param seg1 the Cluster (in the first superlayer) which is used to make a cross
	 */
	public void set_Cluster1(Cluster seg1) {
		this._clus1 = seg1;
	}

	/**
	 * Set the second Cluster (corresponding to the second superlayer in a region)
	 * @param seg2 the Cluster (in the second superlayer) which is used to make a cross
	 */
	public void set_Cluster2(Cluster seg2) {
		this._clus2 = seg2;
	}

	/**
	 * 
	 * @return he Cluster (in the first superlayer) which is used to make a cross
	 */
	public Cluster get_Cluster1() {
		return _clus1;
	}
	
	/**
	 * 
	 * @return the Cluster (in the second superlayer) which is used to make a cross
	 */
	public Cluster get_Cluster2() {
		return _clus2;
	}

	/**
	 * Sets the cross parameters: the position and direction unit vector
	 */
	public void set_CrossParams() {
		
		Cluster Zcluster = this.get_Cluster1();  // Z detector
		Cluster Ccluster = this.get_Cluster2(); // C detector
		
		double phi = Zcluster.get_Phi();
		double phierr = Zcluster.get_PhiErr();
		double z = Ccluster.get_Z();
		double zErr = Ccluster.get_ZErr();
		double R = Constants.CRCRADIUS[Ccluster.get_Region()-1];
		
		double x = R*Math.cos(phi);
		double y = R*Math.sin(phi);
		
		double xErr = -R*Math.sin(phi)*phierr;
		double yErr =  R*Math.cos(phi)*phierr;
		
		this.set_Point(new Point3D(x,y,z));
		this.set_PointErr(new Point3D(xErr,yErr,zErr));
		
	}

	
    private int _AssociatedTrackID=-1;
	
	

	public int get_AssociatedTrackID() {
		return _AssociatedTrackID;
	}

	public void set_AssociatedTrackID(int _AssociatedTrackID) {
		this._AssociatedTrackID = _AssociatedTrackID;
	}
	
	
	public void set_AssociatedElementsIDs() {
		
		for(Cluster cluster : this) {
			cluster.set_AssociatedCrossID(this._Id);
			cluster.set_AssociatedTrackID(this._AssociatedTrackID);
			
			for(FittedHit hit : cluster) {
				hit.set_AssociatedClusterID(cluster.get_Id());
				hit.set_AssociatedCrossID(this._Id);
				hit.set_AssociatedTrackID(this._AssociatedTrackID);
				
			}
		}
		
	}
	
	/**
	 * 
	 * @return the track info.
	 */
	public String printInfo() {
		String s = "BMT cross: ID "+this.get_Id()+" trkID "+ this.get_AssociatedTrackID()+" Sector "+this.get_Sector()+" Region "+this.get_Region()
				+" Point "+this.get_Point().toString();
		return s;
	}

	
	
}

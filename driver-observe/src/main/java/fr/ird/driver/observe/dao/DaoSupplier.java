package fr.ird.driver.observe.dao;

import fr.ird.driver.anapo.dao.PosVMSDAO;
import fr.ird.driver.observe.dao.referential.AbstractReferentialDao;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * To get access to any dao (all dao are instantiated only once).
 * <p>
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class DaoSupplier {
    private final SingletonSupplier<fr.ird.driver.anapo.dao.PosVMSDAO> vmsDao = SingletonSupplier.of(fr.ird.driver.anapo.dao.PosVMSDAO::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.VersionDao> versionDao = SingletonSupplier.of(fr.ird.driver.observe.dao.VersionDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.CountryDao> commonCountryDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.CountryDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.DataQualityDao> commonDtaQualityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.DataQualityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.FpaZoneDao> commonFpaZoneDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.FpaZoneDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.GearCharacteristicTypeDao> commonGearCharacteristicTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.GearCharacteristicTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.OceanDao> commonOceanDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.OceanDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SexDao> commonSexDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SexDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SizeMeasureDao> commonSizeMeasureDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SizeMeasureDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SizeMeasureTypeDao> commonSizeMeasureTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SizeMeasureTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SpeciesGroupReleaseModeDao> commonSpeciesGroupReleaseModeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SpeciesGroupReleaseModeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.VesselSizeCategoryDao> commonVesselSizeCategoryDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.VesselSizeCategoryDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.VesselTypeDao> commonVesselTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.VesselTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.WeightMeasureMethodDao> commonWeightMeasureMethodDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.WeightMeasureMethodDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.WeightMeasureTypeDao> commonWeightMeasureTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.WeightMeasureTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.WindDao> commonWindDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.WindDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.GearCharacteristicDao> commonGearCharacteristicDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.GearCharacteristicDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.HarbourDao> commonHarbourDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.HarbourDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.OrganismDao> commonOrganismDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.OrganismDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.PersonDao> commonPersonDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.PersonDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.ShipOwnerDao> commonShipOwnerDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.ShipOwnerDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SpeciesGroupDao> commonSpeciesGroupDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SpeciesGroupDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.GearDao> commonGearDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.GearDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.SpeciesDao> commonSpeciesDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.SpeciesDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.common.VesselDao> commonVesselDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.common.VesselDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.AcquisitionStatusDao> psCommonAcquisitionStatusDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.AcquisitionStatusDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialTypeDao> psCommonObjectMaterialTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ObjectOperationDao> psCommonObjectOperationDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ObjectOperationDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ReasonForNoFishingDao> psCommonReasonForNoFishingDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ReasonForNoFishingDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ReasonForNullSetDao> psCommonReasonForNullSetDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ReasonForNullSetDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.SampleTypeDao> psCommonSampleTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.SampleTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.SchoolTypeDao> psCommonSchoolTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.SchoolTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.SpeciesFateDao> psCommonSpeciesFateDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.SpeciesFateDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOperationDao> psCommonTransmittingBuoyOperationDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOperationDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOwnershipDao> psCommonTransmittingBuoyOwnershipDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOwnershipDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyTypeDao> psCommonTransmittingBuoyTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.VesselActivityDao> psCommonVesselActivityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.VesselActivityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialDao> psCommonObjectMaterialDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ObservedSystemDao> psCommonObservedSystemDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ObservedSystemDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.ProgramDao> psCommonProgramDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.ProgramDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.common.WeightCategoryDao> psCommonWeightCategoryDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.common.WeightCategoryDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.landing.DestinationDao> psLandingDestinationDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.landing.DestinationDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.landing.FateDao> psLandingLateDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.landing.FateDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.localmarket.BatchCompositionDao> psLocalmarketBatchCompositionDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.localmarket.BatchCompositionDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.localmarket.BatchWeightTypeDao> psLocalmarketBatchWeightTypeDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.localmarket.BatchWeightTypeDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.localmarket.BuyerDao> psLocalmarketBuyerDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.localmarket.BuyerDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.localmarket.PackagingDao> psLocalmarketPackagingDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.localmarket.PackagingDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.InformationSourceDao> psLogbookInformationSourceDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.InformationSourceDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.SampleQualityDao> psLogbookSampleQualityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.SampleQualityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.SetSuccessStatusDao> psLogbookSetSuccessStatusDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.SetSuccessStatusDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.WellContentStatusDao> psLogbookWellContentStatusDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.WellContentStatusDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingConformityDao> psLogbookWellSamplingConformityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingConformityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingStatusDao> psLogbookWellSamplingStatusDao = SingletonSupplier.of(fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingStatusDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.SampleDao> psLogbookSampleDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.SampleDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.ActivityDao> psLogbookActivityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.ActivityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.SampleActivityDao> psLogbookSampleActivityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.SampleActivityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesDao> psLocalmarketSampleSpeciesDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.RouteDao> psLogbookRouteDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.RouteDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesMeasureDao> psLogbookSampleSpeciesMeasureDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesMeasureDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectDao> psLogbookFloatingObjectDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.common.TripDao> psCommonTripDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.common.TripDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectPartDao> psLogbookFloatingObjectPartDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectPartDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.TransmittingBuoyDao> psLogbookTransmittingBuoyDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.TransmittingBuoyDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.SurveyDao> psLocalmarketSurveyDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.SurveyDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.SampleDao> psLocalmarketSampleDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.SampleDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesMeasureDao> psLocalmarketSampleSpeciesMeasureDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesMeasureDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.WellDao> psLogbookWellDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.WellDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.WellActivitySpeciesDao> psLogbookWellActivitySpeciesDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.WellActivitySpeciesDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.CatchDao> psLogbookCatchDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.CatchDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.SurveyPartDao> psLocalmarketSurveyPartDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.SurveyPartDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.localmarket.BatchDao> psLocalmarketBatchDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.localmarket.BatchDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesDao> psLogbookSampleSpeciesDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.logbook.WellActivityDao> psLogbookWellActivityDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.logbook.WellActivityDao::new);
    private final SingletonSupplier<fr.ird.driver.observe.dao.data.ps.landing.LandingDao> psLandingLandingDao = SingletonSupplier.of(fr.ird.driver.observe.dao.data.ps.landing.LandingDao::new);

    /**
     * @return the set of dao to be loaded by {@link fr.ird.driver.observe.dao.referential.ReferentialCache}
     * using the dependency order.
     * @see fr.ird.driver.observe.dao.referential.ReferentialCache#load(DaoSupplier)
     */
    public Set<AbstractReferentialDao<?>> referentialDaoOrder() {
        Set<AbstractReferentialDao<?>> result = new LinkedHashSet<>();
        result.add(getCommonCountryDao());
        result.add(getCommonDataQualityDao());
        result.add(getCommonFpaZoneDao());
        result.add(getCommonGearCharacteristicTypeDao());
        result.add(getCommonOceanDao());
        result.add(getCommonSexDao());
        result.add(getCommonSizeMeasureDao());
        result.add(getCommonSizeMeasureTypeDao());
        result.add(getCommonSpeciesGroupReleaseModeDao());
        result.add(getCommonVesselSizeCategoryDao());
        result.add(getCommonVesselTypeDao());
        result.add(getCommonWeightMeasureMethodDao());
        result.add(getCommonWeightMeasureTypeDao());
        result.add(getCommonWindDao());
        result.add(getCommonGearCharacteristicDao());
        result.add(getCommonHarbourDao());
        result.add(getCommonOrganismDao());
        result.add(getCommonPersonDao());
        result.add(getCommonShipOwnerDao());
        result.add(getCommonSpeciesGroupDao());
        result.add(getCommonGearDao());
        result.add(getCommonSpeciesDao());
        result.add(getCommonVesselDao());
        result.add(getPsCommonAcquisitionStatusDao());
        result.add(getPsCommonObjectMaterialTypeDao());
        result.add(getPsCommonObjectOperationDao());
        result.add(getPsCommonReasonForNoFishingDao());
        result.add(getPsCommonReasonForNullSetDao());
        result.add(getPsCommonSampleTypeDao());
        result.add(getPsCommonSchoolTypeDao());
        result.add(getPsCommonSpeciesFateDao());
        result.add(getPsCommonTransmittingBuoyOperationDao());
        result.add(getPsCommonTransmittingBuoyOwnershipDao());
        result.add(getPsCommonTransmittingBuoyTypeDao());
        result.add(getPsCommonVesselActivityDao());
        result.add(getPsCommonObjectMaterialDao());
        result.add(getPsCommonObservedSystemDao());
        result.add(getPsCommonProgramDao());
        result.add(getPsCommonWeightCategoryDao());
        result.add(getPsLandingDestinationDao());
        result.add(getPsLandingFateDao());
        result.add(getPsLocalmarketBatchCompositionDao());
        result.add(getPsLocalmarketBatchWeightTypeDao());
        result.add(getPsLocalmarketBuyerDao());
        result.add(getPsLocalmarketPackagingDao());
        result.add(getPsLogbookInformationSourceDao());
        result.add(getPsLogbookSampleQualityDao());
        result.add(getPsLogbookSetSuccessStatusDao());
        result.add(getPsLogbookWellContentStatusDao());
        result.add(getPsLogbookWellSamplingConformityDao());
        result.add(getPsLogbookWellSamplingStatusDao());

        return result;
    }

    public fr.ird.driver.anapo.dao.PosVMSDAO getVmsDao() {
        return vmsDao.get();
    }

    public fr.ird.driver.observe.dao.VersionDao getVersionDao() {
        return versionDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.CountryDao getCommonCountryDao() {
        return commonCountryDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.DataQualityDao getCommonDataQualityDao() {
        return commonDtaQualityDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.FpaZoneDao getCommonFpaZoneDao() {
        return commonFpaZoneDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.GearCharacteristicTypeDao getCommonGearCharacteristicTypeDao() {
        return commonGearCharacteristicTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.OceanDao getCommonOceanDao() {
        return commonOceanDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SexDao getCommonSexDao() {
        return commonSexDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SizeMeasureDao getCommonSizeMeasureDao() {
        return commonSizeMeasureDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SizeMeasureTypeDao getCommonSizeMeasureTypeDao() {
        return commonSizeMeasureTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SpeciesGroupReleaseModeDao getCommonSpeciesGroupReleaseModeDao() {
        return commonSpeciesGroupReleaseModeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.VesselSizeCategoryDao getCommonVesselSizeCategoryDao() {
        return commonVesselSizeCategoryDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.VesselTypeDao getCommonVesselTypeDao() {
        return commonVesselTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.WeightMeasureMethodDao getCommonWeightMeasureMethodDao() {
        return commonWeightMeasureMethodDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.WeightMeasureTypeDao getCommonWeightMeasureTypeDao() {
        return commonWeightMeasureTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.WindDao getCommonWindDao() {
        return commonWindDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.GearCharacteristicDao getCommonGearCharacteristicDao() {
        return commonGearCharacteristicDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.HarbourDao getCommonHarbourDao() {
        return commonHarbourDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.OrganismDao getCommonOrganismDao() {
        return commonOrganismDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.PersonDao getCommonPersonDao() {
        return commonPersonDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.ShipOwnerDao getCommonShipOwnerDao() {
        return commonShipOwnerDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SpeciesGroupDao getCommonSpeciesGroupDao() {
        return commonSpeciesGroupDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.GearDao getCommonGearDao() {
        return commonGearDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.SpeciesDao getCommonSpeciesDao() {
        return commonSpeciesDao.get();
    }

    public fr.ird.driver.observe.dao.referential.common.VesselDao getCommonVesselDao() {
        return commonVesselDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.AcquisitionStatusDao getPsCommonAcquisitionStatusDao() {
        return psCommonAcquisitionStatusDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialTypeDao getPsCommonObjectMaterialTypeDao() {
        return psCommonObjectMaterialTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ObjectOperationDao getPsCommonObjectOperationDao() {
        return psCommonObjectOperationDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ReasonForNoFishingDao getPsCommonReasonForNoFishingDao() {
        return psCommonReasonForNoFishingDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ReasonForNullSetDao getPsCommonReasonForNullSetDao() {
        return psCommonReasonForNullSetDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.SampleTypeDao getPsCommonSampleTypeDao() {
        return psCommonSampleTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.SchoolTypeDao getPsCommonSchoolTypeDao() {
        return psCommonSchoolTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.SpeciesFateDao getPsCommonSpeciesFateDao() {
        return psCommonSpeciesFateDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOperationDao getPsCommonTransmittingBuoyOperationDao() {
        return psCommonTransmittingBuoyOperationDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyOwnershipDao getPsCommonTransmittingBuoyOwnershipDao() {
        return psCommonTransmittingBuoyOwnershipDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.TransmittingBuoyTypeDao getPsCommonTransmittingBuoyTypeDao() {
        return psCommonTransmittingBuoyTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.VesselActivityDao getPsCommonVesselActivityDao() {
        return psCommonVesselActivityDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ObjectMaterialDao getPsCommonObjectMaterialDao() {
        return psCommonObjectMaterialDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ObservedSystemDao getPsCommonObservedSystemDao() {
        return psCommonObservedSystemDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.ProgramDao getPsCommonProgramDao() {
        return psCommonProgramDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.common.WeightCategoryDao getPsCommonWeightCategoryDao() {
        return psCommonWeightCategoryDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.landing.DestinationDao getPsLandingDestinationDao() {
        return psLandingDestinationDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.landing.FateDao getPsLandingFateDao() {
        return psLandingLateDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.localmarket.BatchCompositionDao getPsLocalmarketBatchCompositionDao() {
        return psLocalmarketBatchCompositionDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.localmarket.BatchWeightTypeDao getPsLocalmarketBatchWeightTypeDao() {
        return psLocalmarketBatchWeightTypeDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.localmarket.BuyerDao getPsLocalmarketBuyerDao() {
        return psLocalmarketBuyerDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.localmarket.PackagingDao getPsLocalmarketPackagingDao() {
        return psLocalmarketPackagingDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.InformationSourceDao getPsLogbookInformationSourceDao() {
        return psLogbookInformationSourceDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.SampleQualityDao getPsLogbookSampleQualityDao() {
        return psLogbookSampleQualityDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.SetSuccessStatusDao getPsLogbookSetSuccessStatusDao() {
        return psLogbookSetSuccessStatusDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.WellContentStatusDao getPsLogbookWellContentStatusDao() {
        return psLogbookWellContentStatusDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingConformityDao getPsLogbookWellSamplingConformityDao() {
        return psLogbookWellSamplingConformityDao.get();
    }

    public fr.ird.driver.observe.dao.referential.ps.logbook.WellSamplingStatusDao getPsLogbookWellSamplingStatusDao() {
        return psLogbookWellSamplingStatusDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.common.TripDao getPsCommonTripDao() {
        return psCommonTripDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.landing.LandingDao getPsLandingLandingDao() {
        return psLandingLandingDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.SurveyDao getPsLocalmarketSurveyDao() {
        return psLocalmarketSurveyDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.SampleDao getPsLocalmarketSampleDao() {
        return psLocalmarketSampleDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesMeasureDao getPsLocalmarketSampleSpeciesMeasureDao() {
        return psLocalmarketSampleSpeciesMeasureDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.SurveyPartDao getPsLocalmarketSurveyPartDao() {
        return psLocalmarketSurveyPartDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.BatchDao getPsLocalmarketBatchDao() {
        return psLocalmarketBatchDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.localmarket.SampleSpeciesDao getPsLocalmarketSampleSpeciesDao() {
        return psLocalmarketSampleSpeciesDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.SampleDao getPsLogbookSampleDao() {
        return psLogbookSampleDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.ActivityDao getPsLogbookActivityDao() {
        return psLogbookActivityDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.SampleActivityDao getPsLogbookSampleActivityDao() {
        return psLogbookSampleActivityDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.RouteDao getPsLogbookRouteDao() {
        return psLogbookRouteDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesMeasureDao getPsLogbookSampleSpeciesMeasureDao() {
        return psLogbookSampleSpeciesMeasureDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectDao getPsLogbookFloatingObjectDao() {
        return psLogbookFloatingObjectDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.FloatingObjectPartDao getPsLogbookFloatingObjectPartDao() {
        return psLogbookFloatingObjectPartDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.TransmittingBuoyDao getPsLogbookTransmittingBuoyDao() {
        return psLogbookTransmittingBuoyDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.WellDao getPsLogbookWellDao() {
        return psLogbookWellDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.WellActivitySpeciesDao getPsLogbookWellActivitySpeciesDao() {
        return psLogbookWellActivitySpeciesDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.CatchDao getPsLogbookCatchDao() {
        return psLogbookCatchDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.SampleSpeciesDao getPsLogbookSampleSpeciesDao() {
        return psLogbookSampleSpeciesDao.get();
    }

    public fr.ird.driver.observe.dao.data.ps.logbook.WellActivityDao getPsLogbookWellActivityDao() {
        return psLogbookWellActivityDao.get();
    }
}

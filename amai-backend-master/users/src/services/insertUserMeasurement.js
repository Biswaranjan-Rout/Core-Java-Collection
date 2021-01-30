const UM = require("../models").UserMeasurement;

const insertUserMeasurement = async (req, res) => {
  
    const result = await UM.create({
      user_id: req.user.id,
      hip_round:req.body.hip_round,
      nape_to_waist: req.body.nape_to_waist,
      armhale_depth: req.body.armhale_depth,
      shoulder_length:req.body.shoulder_length,
      shoulder_pain_to_paint: req.body.shoulder_pain_to_paint,
      bicep_round: req.body.bicep_round,
      wrist_round:req.body.wrist_round,
      elbow_round: req.body.elbow_round,
      height: req.body.height,
      bust_round: req.body.bust_round,
      waist_round:req.body.waist_round,
      high_hip_round: req.body.high_hip_round
    });
    return result.dataValues;
};

module.exports = insertUserMeasurement;

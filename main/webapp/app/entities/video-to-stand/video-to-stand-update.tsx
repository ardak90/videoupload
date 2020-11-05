import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVideoFile } from 'app/shared/model/video-file.model';
import { getEntities as getVideoFiles } from 'app/entities/video-file/video-file.reducer';
import { IStand } from 'app/shared/model/stand.model';
import { getEntities as getStands } from 'app/entities/stand/stand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './video-to-stand.reducer';
import { IVideoToStand } from 'app/shared/model/video-to-stand.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVideoToStandUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVideoToStandUpdateState {
  isNew: boolean;
  videoFileId: string;
  standId: string;
}

export class VideoToStandUpdate extends React.Component<IVideoToStandUpdateProps, IVideoToStandUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      videoFileId: '0',
      standId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVideoFiles();
    this.props.getStands();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { videoToStandEntity } = this.props;
      const entity = {
        ...videoToStandEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/video-to-stand');
  };

  render() {
    const { videoToStandEntity, videoFiles, stands, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="standsVideouploadApp.videoToStand.home.createOrEditLabel">Create or edit a VideoToStand</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : videoToStandEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="video-to-stand-id">ID</Label>
                    <AvInput id="video-to-stand-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="video-to-stand-videoFile">Video File</Label>
                  <AvInput id="video-to-stand-videoFile" type="select" className="form-control" name="videoFile.id">
                    <option value="" key="0" />
                    {videoFiles
                      ? videoFiles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="video-to-stand-stand">Stand</Label>
                  <AvInput id="video-to-stand-stand" type="select" className="form-control" name="stand.id">
                    <option value="" key="0" />
                    {stands
                      ? stands.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/video-to-stand" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  videoFiles: storeState.videoFile.entities,
  stands: storeState.stand.entities,
  videoToStandEntity: storeState.videoToStand.entity,
  loading: storeState.videoToStand.loading,
  updating: storeState.videoToStand.updating,
  updateSuccess: storeState.videoToStand.updateSuccess
});

const mapDispatchToProps = {
  getVideoFiles,
  getStands,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoToStandUpdate);
